How to run source code on Eclipse?
=================================
1.	Check out code to local directory
2.	Launch Eclipse
3.	File > Import > Maven > Existing Maven Projects
4.	Browse and select folder > click “Finish”
6.	Wait for project to download all dependency
7.	Run as GrantApplication


How to execute JAR file via command line?
=================================
1.	Install Java and JDK 11 or later
2.	Launch CMD
3.	Go to file target directory 
    …\Grant-Disbursement-API\grant\target>
4.	To start application > enter “java -jar grant-1.0.jar” (Application success when you see “Started GrantApplication in 5.374 seconds (JVM running for 5.863)”)
5.	To end application > press CTRL + C on CMD


Connecting to DB
=================================
1.	Run Application and launch http://localhost:8080/h2-console on browser
2.	Enter jdbc:h2:mem:testdb for JBDC URL
3.	Click connect 


HouseHolds RestAPI Calls
=============
1.	GET - http://localhost:8080/households
2.	POST - http://localhost:8080/households 
{
	"houseHoldType": "HDB"
}


3.	GET - http://localhost:8080/households/1 
4.	DELETE - http://localhost:8080/households/1
If not found - Body
{
    "timestamp": "2021-02-04T07:42:44.623+00:00",
    "message": "household id-5 not found",
    "details": "uri=/households/5"
}


5.	GET - http://localhost:8080/households/2/familymembers 
6.	POST - http://localhost:8080/households/2/familymembers
{
    "name": "Jacky",
    "gender": "M",
    "maritalStatus": "single",
    "spouse": "",
    "occupationType": "Student",
    "annualIncome": 0,
    "dob": "2011-02-01T16:00:00.000+00:00"
}


7.	DELETE - http://localhost:8080/households/2/familymembers/206
If not found in households – 
{
    "timestamp": "2021-02-04T07:56:39.315+00:00",
    "message": "household: 3- familymember: 205 not found",
    "details": "uri=/households/3/familymembers/205"
}


Family RestAPI Calls
=============
1.	GET - http://localhost:8080/familymembers 
2.	POST - http://localhost:8080/familymembers
{
    "name": "Jenifer",
    "gender": "F",
    "maritalStatus": "single",
    "spouse": "",
    "occupationType": "Employed",
    "annualIncome": 30000,
    "dob": "1988-02-01T16:00:00.000+00:00"
}


3.	GET - http://localhost:8080/familymembers/201
4.	DELETE - http://localhost:8080/familymembers/3
If familymember not found
{
    "timestamp": "2021-02-04T08:02:13.610+00:00",
    "message": "family member id-214 not found",
    "details": "uri=/familymembers/214"
}



Individual RestAPI Calls
=============
1.	GET - http://localhost:8080/findbyhouseholdtype?household_type=landed 
2.	GET - http://localhost:8080/findbelowage?age=5 
3.	GET - http://localhost:8080/findaboveage?age=50 
4.	GET - http://localhost:8080/findbelowincome?income_limit=90000


Grant RestAPI Calls
=============
1.	GET - http://localhost:8080/student?age=16&income_limit=150000 
2.	GET - http://localhost:8080/family?age=18
3.	GET - http://localhost:8080/elder?age=50&household_type=HDB 
4.	GET - http://localhost:8080/baby?age=5  
5.	GET - http://localhost:8080/yolo?income=100000&household_type=HDB


All in 1 API (Optional Param) REST API Calls
=============
1.	GET - http://localhost:8080/grant?aboveage=50&belowage=18&income=90000&household_type=landed
