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
> db.getCollectionNames()
[ "book", "books", "sampleCollection", "subscribers", "window" ]
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
```
## Query Data
```
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
> db.book.find({$or:[{"Author":"B. R. Bhagwat"},{"Pages": {$lt:200}}]}).pretty()
{
        "_id" : ObjectId("62b46ff56240fd26d5ae76ae"),
        "title" : "Harry Potter 1",
        "Author" : "J. K. Rowling",
        "Pages" : 100
}
{
        "_id" : ObjectId("62b473ed6240fd26d5ae76b3"),
        "title" : "Faster Fene 1",
        "Author" : "B. R. Bhagwat",
        "Pages" : 150
}
>
```
## Update Command
```
> db.book.update({"title":"Harry Potter 1"},{$set:{"title":"Harry Potter and the Philasopher's Stone"}})
WriteResult({ "nMatched" : 1, "nUpserted" : 0, "nModified" : 1 })
> db.book.find({$and:[{"Author":"J. K. Rowling"},{"Pages": {$lt:300}}]}).pretty()
{
        "_id" : ObjectId("62b46ff56240fd26d5ae76ae"),
        "title" : "Harry Potter and the Philasopher's Stone",
        "Author" : "J. K. Rowling",
        "Pages" : 100
}
{
        "_id" : ObjectId("62b470316240fd26d5ae76af"),
        "title" : "Harry Potter 2",
        "Author" : "J. K. Rowling",
        "Pages" : 200
}

> db.book.find({"Author":"B. R. Bhagwat"})
{ "_id" : ObjectId("62b473ed6240fd26d5ae76b3"), "title" : "Faster Fene 1", "Author" : "B. R. Bhagwat", "Pages" : 150 }
{ "_id" : ObjectId("62b479996240fd26d5ae76b4"), "title" : "Faster Fene 2", "Author" : "B. R. Bhagwat", "Pages" : 250 }

> db.book.update({"Author":"B. R. Bhagwat"},{$set:{"Author":"Bha. Ra. Bhagawat"}},{multi:true})
WriteResult({ "nMatched" : 2, "nUpserted" : 0, "nModified" : 2 })

> db.book.find({"Author":"Bha. Ra. Bhagawat"})
{ "_id" : ObjectId("62b473ed6240fd26d5ae76b3"), "title" : "Faster Fene 1", "Author" : "Bha. Ra. Bhagawat", "Pages" : 150 }
{ "_id" : ObjectId("62b479996240fd26d5ae76b4"), "title" : "Faster Fene 2", "Author" : "Bha. Ra. Bhagawat", "Pages" : 250 }
>
```
## Remove Command
```
> db.book.remove({"title" : "Faster Fene 2"})
WriteResult({ "nRemoved" : 1 })
> db.book.find({"Author":"Bha. Ra. Bhagawat"})
{ "_id" : ObjectId("62b473ed6240fd26d5ae76b3"), "title" : "Faster Fene 1", "Author" : "Bha. Ra. Bhagawat", "Pages" : 150 }

> db.book.remove({})
WriteResult({ "nRemoved" : 5 })
>
```
## Find Command - specific fields
```
> db.book.find({},{"title":1,_id:0,"Pages":1})
{ "title" : "Harry Potter 1", "Pages" : 100 }
{ "title" : "Harry Potter 2", "Pages" : 200 }
{ "title" : "Harry Potter 3", "Pages" : 300 }
{ "title" : "Harry Potter 4", "Pages" : 400 }
{ "title" : "Faster Fene 1", "Pages" : 150 }
{ "title" : "Faster Fene 2", "Pages" : 250 }

> db.book.find({},{"title":1,_id:0,"Pages":1}).limit(3)
{ "title" : "Harry Potter 1", "Pages" : 100 }
{ "title" : "Harry Potter 2", "Pages" : 200 }
{ "title" : "Harry Potter 3", "Pages" : 300 }

> db.book.find({},{"title":1,_id:0,"Pages":1}).skip(1)
{ "title" : "Harry Potter 2", "Pages" : 200 }
{ "title" : "Harry Potter 3", "Pages" : 300 }
{ "title" : "Harry Potter 4", "Pages" : 400 }
{ "title" : "Faster Fene 1", "Pages" : 150 }
{ "title" : "Faster Fene 2", "Pages" : 250 }

> db.book.find({},{"title":1,_id:0,"Pages":1}).limit(2).skip(2)
{ "title" : "Harry Potter 3", "Pages" : 300 }
{ "title" : "Harry Potter 4", "Pages" : 400 }

```
## Sorting
```
> db.book.find({},{_id:0}).sort({"Pages":1})
{ "title" : "Harry Potter 1", "Author" : "J. K. Rowling", "Pages" : 100 }
{ "title" : "Faster Fene 1", "Author" : "B. R. Bhagwat", "Pages" : 150 }
{ "title" : "Harry Potter 2", "Author" : "J. K. Rowling", "Pages" : 200 }
{ "title" : "Faster Fene 2", "Author" : "B. R. Bhagwat", "Pages" : 250 }
{ "title" : "Harry Potter 3", "Author" : "J. K. Rowling", "Pages" : 300 }
{ "title" : "Harry Potter 4", "Author" : "J. K. Rowling", "Pages" : 400 }

> db.book.find({},{_id:0}).sort({"Pages":-1})
{ "title" : "Harry Potter 4", "Author" : "J. K. Rowling", "Pages" : 400 }
{ "title" : "Harry Potter 3", "Author" : "J. K. Rowling", "Pages" : 300 }
{ "title" : "Faster Fene 2", "Author" : "B. R. Bhagwat", "Pages" : 250 }
{ "title" : "Harry Potter 2", "Author" : "J. K. Rowling", "Pages" : 200 }
{ "title" : "Faster Fene 1", "Author" : "B. R. Bhagwat", "Pages" : 150 }
{ "title" : "Harry Potter 1", "Author" : "J. K. Rowling", "Pages" : 100 }

>
```
## Indexing
```
> db.book.createIndex({"title":1})
{
        "numIndexesBefore" : 1,
        "numIndexesAfter" : 2,
        "createdCollectionAutomatically" : false,
        "ok" : 1
}
> db.book.createIndex({"title":-1,"Author":1})
{
        "numIndexesBefore" : 2,
        "numIndexesAfter" : 3,
        "createdCollectionAutomatically" : false,
        "ok" : 1
}

> db.book.dropIndexes()
{
        "nIndexesWas" : 4,
        "msg" : "non-_id indexes dropped for collection",
        "ok" : 1
}
>

> db.book.createIndex({"title":1,"Author":1})
{
        "numIndexesBefore" : 1,
        "numIndexesAfter" : 2,
        "createdCollectionAutomatically" : false,
        "ok" : 1
}
> db.book.createIndex({"title":1})
{
        "numIndexesBefore" : 2,
        "numIndexesAfter" : 3,
        "createdCollectionAutomatically" : false,
        "ok" : 1
}

> db.book.getIndexes()
[
        {
                "v" : 2,
                "key" : {
                        "_id" : 1
                },
                "name" : "_id_"
        },
        {
                "v" : 2,
                "key" : {
                        "title" : 1,
                        "Author" : 1
                },
                "name" : "title_1_Author_1"
        },
        {
                "v" : 2,
                "key" : {
                        "title" : 1
                },
                "name" : "title_1"
        }
]
>
```
## Adanced query

 - [Aggregation](/Aggregate.md)
 
