# Relashiopships
Relationship can be modelled by embedding and referencing a document.
```
> db.subscribers.insert(
	{
		"contact": "987654321",
		"dob": "01-01-1981",
		"name": "Captain Cook",
		"address": [
			{
				"building": "Fairlake Trace",
				"pincode": 123456,
				"city": "Weston",
				"state": "Floria"
			},
			{
				"building": "123, Sea View Apt",
				"pincode": 456789,
				"city": "Miami",
				"state": "Florida"
			}
		],
		"borrow" : [
		ObjectId("62b47bb86240fd26d5ae76b5"),
      	ObjectId("62b47bb86240fd26d5ae76b8")
		]
	}
)
```
Above statment will create a subscriber entry with address as a embedded relationship and borrow as referenced relationship.
The insert command will not check for referential integrity. As shown in following example it is possible to insert object ids which don't exist in database. 
Also if we assign same book to two subscribers, it will not show any error.

```
> db.subscribers.insert(
	{
		"contact": "987654321",
		"dob": "01-12-1986",
		"name": "Jack Sparrow",
		"address": [
			{
				"building": "342, Beach Lake",
				"pincode": 456789,
				"city": "Miami",
				"state": "Florida"
			}
		],
		"borrow" : [
		ObjectId("62b47bb86240fd26d5ae76c5"),
      	ObjectId("62b47bb86240fd26d5ae76c8")
		]
	}
)
```
We can update document to correct object ids of borrwed books
```
db.subscribers.update({"name": "Jack Sparrow"},{$set:{"borrow" : [
		ObjectId("62b47bb86240fd26d5ae76b7"),
      	ObjectId("62b47bb86240fd26d5ae76ba")
		]}})
```
## Finding reference documents
You can get list of borrowed book ids in a variable and then search book using the id. 
```
var result = db.subscribers.findOne({"name":"Jack Sparrow"},{"borrow":1})
var addresses = db.book.find({"_id":{"$in":result["borrow"]}})
```
