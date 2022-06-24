# Capped Collection

## Creating Capped Collection
Capped collection allows to limit data and records in collection. This collection can help if there is requirement to keep latest n number of records. 
When n+1 record is added to collection, then record at 1st position is removed.
```
> db.createCollection("window",{capped : true, autoIndexId : true, size : 40000, max : 10})
{
        "note" : "The autoIndexId option is deprecated and will be removed in a future release",
        "ok" : 1
}

> db.window.insertMany([{"record" : 1, "message" : "Hello World"},
... {"record" : 2, "message" : "Hello World"},
... {"record" : 3, "message" : "Hello World"},
... {"record" : 4, "message" : "Hello World"},
... {"record" : 5, "message" : "Hello World"},
... {"record" : 6, "message" : "Hello World"},
... {"record" : 7, "message" : "Hello World"},
... {"record" : 8, "message" : "Hello World"},
... {"record" : 9, "message" : "Hello World"},
... {"record" : 10, "message" : "Hello World"}])
{
        "acknowledged" : true,
        "insertedIds" : [
                ObjectId("62b596120640b376f001711e"),
                ObjectId("62b596120640b376f001711f"),
                ObjectId("62b596120640b376f0017120"),
                ObjectId("62b596120640b376f0017121"),
                ObjectId("62b596120640b376f0017122"),
                ObjectId("62b596120640b376f0017123"),
                ObjectId("62b596120640b376f0017124"),
                ObjectId("62b596120640b376f0017125"),
                ObjectId("62b596120640b376f0017126"),
                ObjectId("62b596120640b376f0017127")
        ]
}

> db.window.find()
{ "_id" : ObjectId("62b596120640b376f001711e"), "record" : 1, "message" : "Hello World" }
{ "_id" : ObjectId("62b596120640b376f001711f"), "record" : 2, "message" : "Hello World" }
{ "_id" : ObjectId("62b596120640b376f0017120"), "record" : 3, "message" : "Hello World" }
{ "_id" : ObjectId("62b596120640b376f0017121"), "record" : 4, "message" : "Hello World" }
{ "_id" : ObjectId("62b596120640b376f0017122"), "record" : 5, "message" : "Hello World" }
{ "_id" : ObjectId("62b596120640b376f0017123"), "record" : 6, "message" : "Hello World" }
{ "_id" : ObjectId("62b596120640b376f0017124"), "record" : 7, "message" : "Hello World" }
{ "_id" : ObjectId("62b596120640b376f0017125"), "record" : 8, "message" : "Hello World" }
{ "_id" : ObjectId("62b596120640b376f0017126"), "record" : 9, "message" : "Hello World" }
{ "_id" : ObjectId("62b596120640b376f0017127"), "record" : 10, "message" : "Hello World" }
```
## Adding document above max capacity
In a capped collection - window, we ahve added 10 documents. The collection is capped at amx 10 documents. By adding a new document, first document is purged.
```
> db.window.insertOne({"record" : 11, "message" : "Hello World 11"})
{
        "acknowledged" : true,
        "insertedId" : ObjectId("62b5963c0640b376f0017128")
}

> db.window.find()
{ "_id" : ObjectId("62b596120640b376f001711f"), "record" : 2, "message" : "Hello World" }
{ "_id" : ObjectId("62b596120640b376f0017120"), "record" : 3, "message" : "Hello World" }
{ "_id" : ObjectId("62b596120640b376f0017121"), "record" : 4, "message" : "Hello World" }
{ "_id" : ObjectId("62b596120640b376f0017122"), "record" : 5, "message" : "Hello World" }
{ "_id" : ObjectId("62b596120640b376f0017123"), "record" : 6, "message" : "Hello World" }
{ "_id" : ObjectId("62b596120640b376f0017124"), "record" : 7, "message" : "Hello World" }
{ "_id" : ObjectId("62b596120640b376f0017125"), "record" : 8, "message" : "Hello World" }
{ "_id" : ObjectId("62b596120640b376f0017126"), "record" : 9, "message" : "Hello World" }
{ "_id" : ObjectId("62b596120640b376f0017127"), "record" : 10, "message" : "Hello World" }
{ "_id" : ObjectId("62b5963c0640b376f0017128"), "record" : 11, "message" : "Hello World 11" }

> db.window.insertOne({"record" : 12, "message" : "Hello World 12"})
{
        "acknowledged" : true,
        "insertedId" : ObjectId("62b5966b0640b376f0017129")
}

> db.window.find()
{ "_id" : ObjectId("62b596120640b376f0017120"), "record" : 3, "message" : "Hello World" }
{ "_id" : ObjectId("62b596120640b376f0017121"), "record" : 4, "message" : "Hello World" }
{ "_id" : ObjectId("62b596120640b376f0017122"), "record" : 5, "message" : "Hello World" }
{ "_id" : ObjectId("62b596120640b376f0017123"), "record" : 6, "message" : "Hello World" }
{ "_id" : ObjectId("62b596120640b376f0017124"), "record" : 7, "message" : "Hello World" }
{ "_id" : ObjectId("62b596120640b376f0017125"), "record" : 8, "message" : "Hello World" }
{ "_id" : ObjectId("62b596120640b376f0017126"), "record" : 9, "message" : "Hello World" }
{ "_id" : ObjectId("62b596120640b376f0017127"), "record" : 10, "message" : "Hello World" }
{ "_id" : ObjectId("62b5963c0640b376f0017128"), "record" : 11, "message" : "Hello World 11" }
{ "_id" : ObjectId("62b5966b0640b376f0017129"), "record" : 12, "message" : "Hello World 12" }
```
Oldest document is purged when new document is added. By removing latest document, we don't get document which was removed due to max document setting. So this is not a sliding window.
```
> db.window.remove({ "_id" : ObjectId("62b5966b0640b376f0017129")})
WriteResult({ "nRemoved" : 1 })
> db.window.find()
{ "_id" : ObjectId("62b596120640b376f0017120"), "record" : 3, "message" : "Hello World" }
{ "_id" : ObjectId("62b596120640b376f0017121"), "record" : 4, "message" : "Hello World" }
{ "_id" : ObjectId("62b596120640b376f0017122"), "record" : 5, "message" : "Hello World" }
{ "_id" : ObjectId("62b596120640b376f0017123"), "record" : 6, "message" : "Hello World" }
{ "_id" : ObjectId("62b596120640b376f0017124"), "record" : 7, "message" : "Hello World" }
{ "_id" : ObjectId("62b596120640b376f0017125"), "record" : 8, "message" : "Hello World" }
{ "_id" : ObjectId("62b596120640b376f0017126"), "record" : 9, "message" : "Hello World" }
{ "_id" : ObjectId("62b596120640b376f0017127"), "record" : 10, "message" : "Hello World" }
{ "_id" : ObjectId("62b5963c0640b376f0017128"), "record" : 11, "message" : "Hello World 11" }
>
```
