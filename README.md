RBAC app developed in core java.
For in memory DB "Sqlite" used.

For comfort to play with app I have commited sqlite files also.

** Please change value for "url" according to your path of application for "DB connection" in src/db/SqliteDB.java".
** In sqlite go to path for exe of sqlite and run some command.
Let my sqlite at  "C:\Users\Lenovo\Desktop\rbac\sqlite\sqlite-tools\sqlite-tools-win32-x86-3350500"
1. cd C:\Users\Lenovo\Desktop\rbac\sqlite\sqlite-tools\sqlite-tools-win32-x86-3350500
2. prompt @$f
3. sqlite3 rbac.db

Then create some tables:

1. create table if not exists users (id INTEGER PRIMARY KEY, name TEXT NOT NULL, email TEXT NOT NULL UNIQUE, password TEXT NOT NULL);
2. create table if not exists roles (id INTEGER PRIMARY KEY, name TEXT NOT NULL UNIQUE);
3. create table if not exists resources (id INTEGER PRIMARY KEY, name TEXT NOT NULL UNIQUE);
4. create table if not exists actions (id INTEGER PRIMARY KEY, name TEXT NOT NULL UNIQUE);
5. create table if not exists user_roles (user_id INTEGER, role_id INTEGER, FOREIGN KEY(user_id) REFERENCES users(id),FOREIGN KEY(role_id) REFERENCES roles(id));
6. create table if not exists role_resource_action (role_id INTEGER, resource_id INTEGER, action_id INTEGER, FOREIGN KEY(role_id) REFERENCES roles(id),FOREIGN KEY(resource_id) REFERENCES resources(id), FOREIGN KEY(action_id) REFERENCES actions(id));

When app runs first time then "admin" will create but not other users: you can create other by app.
ACTION TYPE [READ, WRITE, DELETE] will create in db when app run first time.

"admin" will by default logged in:
Instruction will popup when app runs[FOR ADMIN]:
PRESS '1'	: TO CREATE USER. -> For create user
PRESS '11'	: TO GET LIST OF USERS. -> To get Users other than admin
PRESS '12'	: TO ASSIGN ROLE TO USER. -> To assign role to perticular user
PRESS '13'	: TO CHECK PERMISSION FOR USER FOR PERTICULER RESOURCE & ACTION,  ---> To check user has permission for perticular resource for perticular action
PRESS '2'	: TO CREATE ROLE -> To create Role in DB 
PRESS '21'	: TO GET LIST OF ROLES. ->TO get List Of ALL ROLES
PRESS '22'	: TO GIVE ACCESS TO ROLE FOR RESOURCE WITH ACTION -> TO Provide access for resource for perticular role with action
PRESS '3'	: TO CREATE RESOURCE -> TO create resource
PRESS '31'	: TO GET LIST OF RESOURCES. -> TO get All Resource in DB
PRESS '4'	: TO GET LIST OF ACTIONS. -> BE DEFAULT # actions are there
PRESS '5'	: TO LOGIN AS ANOTHER USER, -> To change user
PRESS '10'	: TO CLOSE APP.-> TO stop App


Now Menu for normal USer:
PRESS '1'	: TO CHECK PERMISSION FOR USER FOR PERTICULER RESOURCE & ACTION, ---> To check user has permission for perticular resource for perticular action
PRESS '2'	: TO GET LIST OF ASSIGNED ROLES, --> User can check which role he has.
PRESS '5'	: TO LOGIN AS ANOTHER USER, ---> To change User
PRESS '10'	: TO CLOSE APP, ---> To stop App
