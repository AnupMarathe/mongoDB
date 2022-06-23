# Mongo DB
## Database Commands
The command use DATABASE_NAME will either create and connect if database is not present or just connects to database. To check to whcih database we are connected, use db command. We can list databases using command show dbs. If there is no data in database, then it is not listed in output of show command. After data is inserted the database shows in show db command.
To drop database, use command db.dropDatabase() while connected to db.
```
> use Library
switched to db Library
> show dbs
admin   0.000GB
config  0.000GB
local   0.000GB
> db
Library
> db.book.insert({"Title":"The Book","Author":"Anon"})
WriteResult({ "nInserted" : 1 })
> show dbs
Library  0.000GB
admin    0.000GB
config   0.000GB
local    0.000GB
> db.dropDatabase()
{ "ok" : 1 }
>
```
## Collection Commands
The command db.createCollection("COLLECTION_NAME") creates a new collection with name - COLLECTION_NAME. If we insert a record and if the collection is not yet created then first it will create a collection and then insert a record.
To list collections present in database use command show collections. To drop a collection use command db.COLLECTION_NAME.drop().

```
> db
Library
> db.createCollection("subscribers")
{ "ok" : 1 }
> db.createCollection("mycollection",{capped : true, autoIndexId : true, size : 33554432, max : 10000})
{
        "note" : "The autoIndexId option is deprecated and will be removed in a future release",
        "ok" : 1
}
>
> show collections
book
mycollection
subscribers
> db.mycollection.drop()
true
> show collections
book
subscribers
> db.book.renameCollection("books")
{ "ok" : 1 }
> show collections
books
subscribers
>
```
## Insert Data
```
> db.book.insertOne({"title":"Harry Potter","Author":"J. K. Rowling"})
{
        "acknowledged" : true,
        "insertedId" : ObjectId("62b46ff56240fd26d5ae76ae")
}
> db.book.insertMany([{"title":"Harry Potter 2","Author":"J. K. Rowling"},])
{
        "acknowledged" : true,
        "insertedIds" : [
                ObjectId("62b470316240fd26d5ae76af")
        ]
}
> db.book.insertMany([{"title":"Harry Potter 3","Author":"J. K. Rowling"},
... {"title":"Harry Potter 4","Author":"J. K. Rowling"}])
{
        "acknowledged" : true,
        "insertedIds" : [
                ObjectId("62b4705b6240fd26d5ae76b0"),
                ObjectId("62b4705b6240fd26d5ae76b1")
        ]
}
> db.book.save({"_id":ObjectId("62b46ff56240fd26d5ae76ae"),"title":"Harry Potter","Author":"J. K. Rowling"})
WriteResult({ "nMatched" : 1, "nUpserted" : 0, "nModified" : 0 })
> db.book.save({"_id":ObjectId("62b46ff56240fd26d5ae76ae"),"title":"Harry Potter 1","Author":"J. K. Rowling"})
WriteResult({ "nMatched" : 1, "nUpserted" : 0, "nModified" : 1 })
>
> db.book.save({ "_id" : ObjectId("62b46ff56240fd26d5ae76ae"), "title" : "Harry Potter 1", "Author" : "J. K. Rowling", "Pages": 100 })
WriteResult({ "nMatched" : 1, "nUpserted" : 0, "nModified" : 1 })
> db.book.save({ "_id" : ObjectId("62b470316240fd26d5ae76af"), "title" : "Harry Potter 2", "Author" : "J. K. Rowling", "Pages": 200 })
WriteResult({ "nMatched" : 1, "nUpserted" : 0, "nModified" : 1 })
> db.book.save({ "_id" : ObjectId("62b4705b6240fd26d5ae76b0"), "title" : "Harry Potter 3", "Author" : "J. K. Rowling", "Pages": 300 })
WriteResult({ "nMatched" : 1, "nUpserted" : 0, "nModified" : 1 })
> db.book.save({ "_id" : ObjectId("62b4705b6240fd26d5ae76b1"), "title" : "Harry Potter 4", "Author" : "J. K. Rowling", "Pages": 400 })
WriteResult({ "nMatched" : 1, "nUpserted" : 0, "nModified" : 1 })
> db.book.findOne({"Author":"J. K. Rowling"})
{
        "_id" : ObjectId("62b46ff56240fd26d5ae76ae"),
        "title" : "Harry Potter 1",
        "Author" : "J. K. Rowling",
        "Pages" : 100
}
> db.book.find({"Author":"J. K. Rowling"})
{ "_id" : ObjectId("62b46ff56240fd26d5ae76ae"), "title" : "Harry Potter 1", "Author" : "J. K. Rowling", "Pages" : 100 }
{ "_id" : ObjectId("62b470316240fd26d5ae76af"), "title" : "Harry Potter 2", "Author" : "J. K. Rowling", "Pages" : 200 }
{ "_id" : ObjectId("62b4705b6240fd26d5ae76b0"), "title" : "Harry Potter 3", "Author" : "J. K. Rowling", "Pages" : 300 }
{ "_id" : ObjectId("62b4705b6240fd26d5ae76b1"), "title" : "Harry Potter 4", "Author" : "J. K. Rowling", "Pages" : 400 }
>
> db.book.find({"Pages":{$lt:250}})
{ "_id" : ObjectId("62b46ff56240fd26d5ae76ae"), "title" : "Harry Potter 1", "Author" : "J. K. Rowling", "Pages" : 100 }
{ "_id" : ObjectId("62b470316240fd26d5ae76af"), "title" : "Harry Potter 2", "Author" : "J. K. Rowling", "Pages" : 200 }
> db.book.find({$and:[{"Author":"J. K. Rowling"},{"Pages": {$gt:200}}]}).pretty()
{
        "_id" : ObjectId("62b4705b6240fd26d5ae76b0"),
        "title" : "Harry Potter 3",
        "Author" : "J. K. Rowling",
        "Pages" : 300
}
{
        "_id" : ObjectId("62b4705b6240fd26d5ae76b1"),
        "title" : "Harry Potter 4",
        "Author" : "J. K. Rowling",
        "Pages" : 400
}
> db.book.find({$and:[{"Author":"J. K. Rowling"},{"Pages": {$gt:300}}]}).pretty()
{
        "_id" : ObjectId("62b4705b6240fd26d5ae76b1"),
        "title" : "Harry Potter 4",
        "Author" : "J. K. Rowling",
        "Pages" : 400
}
>
```
