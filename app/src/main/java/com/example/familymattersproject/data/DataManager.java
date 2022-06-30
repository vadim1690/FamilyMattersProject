package com.example.familymattersproject.data;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.familymattersproject.entities.FamilyEntity;
import com.example.familymattersproject.entities.FamilyEventEntity;
import com.example.familymattersproject.entities.TodoTaskEntity;
import com.example.familymattersproject.entities.UpdateEntity;
import com.example.familymattersproject.entities.UserEntity;
import com.firebase.ui.auth.data.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class DataManager {

    private final String FAMILY_TODOLIST_DB_KEY = "todoList";
    private final String FAMILY_TODOLIST_ARCHIVED_DB_KEY = "todoListArchived";
    private final String FAMILY_EVENTS_DB_KEY = "events";
    private final String TAG = "error_tag";
    private final String FAMILIES_DB_KEY = "Families";
    private final String FAMILY_MEMBERS_DB_KEY = "members";
    private final String USERS_DB_KEY = "Users";
    private final String AVATARS_DB_KEY = "Avatars";
    private final String FAMILY_UPDATES_DB_KEY = "updates";


    private static DataManager INSTANCE = null;
    private final FirebaseDatabase database;
    private FirebaseUser firebaseUser;
    private UserEntity userEntity;
    private FamilyEntity familyEntity;

    private ValueEventListener updatesValueEventListener;
    private ValueEventListener familyEventsValueEventListener;
    private ValueEventListener todoListValueEventListener;
    private ValueEventListener signedInUserValueEventListener;

    private DataManager() {
        database = FirebaseDatabase.getInstance();
        setCurrentFirebaseUser();
    }

    public static DataManager getInstance() {
        if (INSTANCE == null)
            INSTANCE = new DataManager();
        return INSTANCE;
    }



    /* --------------------READ--------------------------------*/

    public void handleSignedInUser(Callback_handleSignedInUser callback_handleSignedInUser) {
        DatabaseReference usersReference = database.getReference(USERS_DB_KEY).child(firebaseUser.getUid());

        signedInUserValueEventListener  = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                userEntity = dataSnapshot.getValue(UserEntity.class);
                if (userEntity != null)
                    handleSignedInUserFamily(callback_handleSignedInUser);
                else
                    callback_handleSignedInUser.isUserExist(false);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "Failed to read value.", error.toException());
            }
        };

        // Read from the database
        usersReference.addValueEventListener(signedInUserValueEventListener);
    }

    private void handleSignedInUserFamily(Callback_handleSignedInUser callback_handleSignedInUser) {
        DatabaseReference familyReference = database.getReference(FAMILIES_DB_KEY).child(userEntity.getFamilyUID());
        familyReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                familyEntity = snapshot.getValue(FamilyEntity.class);
                if (callback_handleSignedInUser != null) {
                    callback_handleSignedInUser.isUserExist(familyEntity != null);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void readUserEntity() {
        DatabaseReference usersReference = database.getReference(USERS_DB_KEY).child(firebaseUser.getUid());
        // Read from the database
        usersReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                userEntity = dataSnapshot.getValue(UserEntity.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "Failed to read value.", error.toException());
            }
        });


    }

    public void readFamilyEntity() {
        DatabaseReference familyReference = database.getReference(FAMILIES_DB_KEY).child(userEntity.getFamilyUID());
        familyReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                familyEntity = snapshot.getValue(FamilyEntity.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void setUserAvatar(UserEntity userEntity) {
        DatabaseReference avatarsReference = database.getReference(AVATARS_DB_KEY);

        avatarsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int size = (int) snapshot.getChildrenCount();
                String key = String.valueOf(new Random().nextInt(size) + 1);
                userEntity.setAvatar(snapshot.child(key).getValue(String.class));
                saveUserEntityInDatabase(userEntity);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getUpdatesList(Callback_setFamilyUpdates callback_setFamilyUpdates) {
        DatabaseReference updatesReference = database.getReference(FAMILIES_DB_KEY).child(familyEntity.getUID()).child(FAMILY_UPDATES_DB_KEY);
        updatesValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                List<UpdateEntity> updateEntities = new ArrayList<>();
                for (DataSnapshot update : dataSnapshot.getChildren()) {
                    updateEntities.add(update.getValue(UpdateEntity.class));
                }
                updateEntities.sort((o1, o2) -> o2.getDate().compareTo(o1.getDate()));
                if (callback_setFamilyUpdates != null)
                    callback_setFamilyUpdates.familyUpdates(updateEntities);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        };
        updatesReference.addValueEventListener(updatesValueEventListener);
    }

    public void getFamilyEvents(Callback_setFamilyEvents callback_setFamilyEvents) {
        DatabaseReference eventsReference = database.getReference(FAMILIES_DB_KEY).child(familyEntity.getUID()).child(FAMILY_EVENTS_DB_KEY);
        familyEventsValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<FamilyEventEntity> familyEventEntities = new ArrayList<>();
                for (DataSnapshot event : dataSnapshot.getChildren()) {
                    familyEventEntities.add(event.getValue(FamilyEventEntity.class));
                }
                familyEventEntities.sort((o1, o2) -> o1.getDate().compareTo(o2.getDate()));
                if (callback_setFamilyEvents != null)
                    callback_setFamilyEvents.familyEvents(familyEventEntities);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        };

        eventsReference.addValueEventListener(familyEventsValueEventListener);
    }

    public void getFamilyMembers(Callback_setFamilyMember callback_setFamilyMember) {
        familyEntity.getMembers().keySet().forEach(userId -> getUserForFamilyMember(userId, callback_setFamilyMember));
    }

    private void getUserForFamilyMember(String userId, Callback_setFamilyMember callback_setFamilyMember) {
        DatabaseReference userReference = database.getReference(USERS_DB_KEY).child(userId);

        userReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                UserEntity userEntity = dataSnapshot.getValue(UserEntity.class);
                if (userEntity != null && callback_setFamilyMember != null)
                    callback_setFamilyMember.familyMember(userEntity);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    public void getFamilyTodoList(Callback_setFamilyTodoList callback_setFamilyTodoList, boolean isArchived) {
        DatabaseReference todoListReference;
        if (isArchived)
            todoListReference = database.getReference(FAMILIES_DB_KEY).child(familyEntity.getUID()).child(FAMILY_TODOLIST_ARCHIVED_DB_KEY);
        else
            todoListReference = database.getReference(FAMILIES_DB_KEY).child(familyEntity.getUID()).child(FAMILY_TODOLIST_DB_KEY);

        todoListValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                List<TodoTaskEntity> todoTaskEntities = new ArrayList<>();
                for (DataSnapshot task : dataSnapshot.getChildren()) {
                    TodoTaskEntity todoTaskEntity = task.getValue(TodoTaskEntity.class);
                    if (todoTaskEntity != null)
                        todoTaskEntities.add(todoTaskEntity);
                }
                if (callback_setFamilyTodoList != null)
                    callback_setFamilyTodoList.familyTodoList(todoTaskEntities);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        };
        // Read from the database
        todoListReference.addValueEventListener(todoListValueEventListener);

    }


    /* --------------------WRITE-------------------------------------------------------------------------------------------------------*/

    public void createNewFamily(String familyName) {
        DatabaseReference familiesReference = database.getReference(FAMILIES_DB_KEY);
        familyEntity = new FamilyEntity();
        familyEntity.setName(familyName);
        familyEntity.setCreator(firebaseUser.getUid());
        familiesReference.child(familyEntity.getUID()).setValue(familyEntity);
    }

    public void createNewJoiningUser(String information , String name, String familyUid) {
        createNewUserInDataBase(information,name, familyUid);
    }

    public void createNewUser(String information , String name) {
        createNewUserInDataBase(information,name, familyEntity.getUID());
    }

    private void createNewUserInDataBase(String information ,String name, String familyUid) {
        userEntity = new UserEntity(firebaseUser.getUid(), name, information);
        userEntity.setUID(firebaseUser.getUid());
        userEntity.setFamilyUID(familyUid);
        setUserAvatar(userEntity);
    }



    private void saveUserEntityInDatabase(UserEntity userEntity) {
        DatabaseReference usersReference = database.getReference(USERS_DB_KEY);
        usersReference.child(userEntity.getUID()).setValue(userEntity);
    }

    public void addFamilyEvent(FamilyEventEntity familyEventEntity) {
        DatabaseReference eventsReference = database.getReference(FAMILIES_DB_KEY).child(familyEntity.getUID()).child(FAMILY_EVENTS_DB_KEY);
        DatabaseReference updatesReference = database.getReference("Families").child(familyEntity.getUID()).child(FAMILY_UPDATES_DB_KEY);


        String text = userEntity.getName() + " created new event";
        UpdateEntity updateEntity = new UpdateEntity(text, userEntity.getName(), new Date());
        updateEntity.setRelatedToIconPath(userEntity.getAvatar());
        eventsReference.child(familyEventEntity.getUID()).setValue(familyEventEntity);
        updatesReference.child(updateEntity.getUID()).setValue(updateEntity);
    }

    public void deleteFamilyEvent(FamilyEventEntity familyEventEntity) {
        DatabaseReference eventsReference = database.getReference(FAMILIES_DB_KEY).child(familyEntity.getUID()).child(FAMILY_EVENTS_DB_KEY);
        eventsReference.child(familyEventEntity.getUID()).removeValue();
    }

    public void addUserToFamily() {
        addUserToFamilyInDatabase(familyEntity.getUID());
    }

    public void addUserToFamily(String familyUid) {
        addUserToFamilyInDatabase(familyUid);
    }

    private void addUserToFamilyInDatabase(String familyUid) {
        DatabaseReference familyReference = database.getReference(FAMILIES_DB_KEY).child(familyUid);
        familyReference.child(FAMILY_MEMBERS_DB_KEY).child(userEntity.getUID()).setValue(true);
    }

    public void todoTaskChecked(TodoTaskEntity todoTaskEntity) {
        DatabaseReference todoListArchivedReference = database.getReference(FAMILIES_DB_KEY).child(familyEntity.getUID()).child(FAMILY_TODOLIST_ARCHIVED_DB_KEY);
        DatabaseReference todoListReference = database.getReference(FAMILIES_DB_KEY).child(familyEntity.getUID()).child(FAMILY_TODOLIST_DB_KEY);
        DatabaseReference updatesReference = database.getReference(FAMILIES_DB_KEY).child(familyEntity.getUID()).child(FAMILY_UPDATES_DB_KEY);

        String text = userEntity.getName() + " Checked " + "'" + todoTaskEntity.getDescription() + "' as done";
        UpdateEntity updateEntity = new UpdateEntity(text, userEntity.getName(), new Date());
        updateEntity.setRelatedToIconPath(userEntity.getAvatar());
        todoListArchivedReference.child(todoTaskEntity.getUID()).setValue(todoTaskEntity);
        todoListReference.child(todoTaskEntity.getUID()).removeValue();
        updatesReference.child(updateEntity.getUID()).setValue(updateEntity);
    }

    public void addFamilyTodoTask(TodoTaskEntity todoTaskEntity) {
        DatabaseReference todoListReference = database.getReference(FAMILIES_DB_KEY).child(familyEntity.getUID()).child(FAMILY_TODOLIST_DB_KEY);
        DatabaseReference updatesReference = database.getReference(FAMILIES_DB_KEY).child(familyEntity.getUID()).child(FAMILY_UPDATES_DB_KEY);
        todoTaskEntity.setCreatedByIconPath(userEntity.getAvatar());
        todoListReference.child(todoTaskEntity.getUID()).setValue(todoTaskEntity);

        String text = userEntity.getName() + " created new Task in to-do list ";
        UpdateEntity updateEntity = new UpdateEntity(text, userEntity.getName(), new Date());
        updateEntity.setRelatedToIconPath(userEntity.getAvatar());
        updatesReference.child(updateEntity.getUID()).setValue(updateEntity);
    }

    public void todoTaskUnchecked(TodoTaskEntity todoTaskEntity) {
        DatabaseReference todoListArchivedReference = database.getReference(FAMILIES_DB_KEY).child(familyEntity.getUID()).child(FAMILY_TODOLIST_ARCHIVED_DB_KEY);
        DatabaseReference todoListReference = database.getReference(FAMILIES_DB_KEY).child(familyEntity.getUID()).child(FAMILY_TODOLIST_DB_KEY);
        todoListReference.child(todoTaskEntity.getUID()).setValue(todoTaskEntity);
        todoListArchivedReference.child(todoTaskEntity.getUID()).removeValue();
    }


    /*----------------------------GENERAL-----------------------------------------------------------------------------------------*/

    public void setCurrentFirebaseUser() {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    }


    public boolean isUserSignedIn() {
        return firebaseUser != null;
    }

    public String getFamilyName() {
        return familyEntity.getName();
    }

    public String getUserName() {
        return userEntity.getName();
    }

    public void removeAllEventListeners() {
        if(signedInUserValueEventListener != null)
            database.getReference(USERS_DB_KEY).child(firebaseUser.getUid());
        if (updatesValueEventListener != null)
            database.getReference(FAMILIES_DB_KEY).child(familyEntity.getUID()).child(FAMILY_UPDATES_DB_KEY).removeEventListener(updatesValueEventListener);
        if (familyEventsValueEventListener != null)
            database.getReference(FAMILIES_DB_KEY).child(familyEntity.getUID()).child(FAMILY_EVENTS_DB_KEY).removeEventListener(familyEventsValueEventListener);
        if (todoListValueEventListener != null) {
            database.getReference(FAMILIES_DB_KEY).child(familyEntity.getUID()).child(FAMILY_TODOLIST_ARCHIVED_DB_KEY).removeEventListener(todoListValueEventListener);
            database.getReference(FAMILIES_DB_KEY).child(familyEntity.getUID()).child(FAMILY_TODOLIST_DB_KEY).removeEventListener(todoListValueEventListener);
        }
    }


    public String getUserAvatar() {
        return userEntity.getAvatar();
    }

    public Date getUserJoinDate() {
        return userEntity.getJoinDate();
    }

    public String getUserInformation() {
        return userEntity.getInformation();
    }

    public void userSignedOut() {
        INSTANCE = null;
    }



    public interface Callback_handleSignedInUser {
        void isUserExist(boolean isExist);
    }

    public interface Callback_setFamilyUpdates {
        void familyUpdates(List<UpdateEntity> updates);
    }

    public interface Callback_setFamilyEvents {
        void familyEvents(List<FamilyEventEntity> events);
    }

    public interface Callback_setFamilyTodoList {
        void familyTodoList(List<TodoTaskEntity> todoList);
    }

    public interface Callback_setFamilyMember {
        void familyMember(UserEntity familyMember);
    }

}



