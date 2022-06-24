## Aggregation
```
> db.book.find({})
{ "_id" : ObjectId("62b47bb86240fd26d5ae76b5"), "title" : "Harry Potter 1", "Author" : "J. K. Rowling", "Pages" : 100, "Cost" : 111, "Likes" : 50 }
{ "_id" : ObjectId("62b47bb86240fd26d5ae76b6"), "title" : "Harry Potter 2", "Author" : "J. K. Rowling", "Pages" : 200, "Cost" : 234, "Likes" : 50 }
{ "_id" : ObjectId("62b47bb86240fd26d5ae76b7"), "title" : "Harry Potter 3", "Author" : "J. K. Rowling", "Pages" : 300, "Cost" : 115, "Likes" : 60 }
{ "_id" : ObjectId("62b47bb86240fd26d5ae76b8"), "title" : "Harry Potter 4", "Author" : "J. K. Rowling", "Pages" : 400, "Cost" : 211, "Likes" : 75 }
{ "_id" : ObjectId("62b47bb86240fd26d5ae76b9"), "title" : "Faster Fene 1", "Author" : "B. R. Bhagwat", "Pages" : 150, "Cost" : 311, "Likes" : 140 }
{ "_id" : ObjectId("62b47bb86240fd26d5ae76ba"), "title" : "Faster Fene 2", "Author" : "B. R. Bhagwat", "Pages" : 250, "Cost" : 111, "Likes" : 150 }

> db.book.aggregate([{$group : {_id : "$Author", Cost : {$min : "$Cost"}}}])
{ "_id" : "B. R. Bhagwat", "Cost" : 111 }
{ "_id" : "J. K. Rowling", "Cost" : 111 }

> db.book.aggregate([{$group : {_id : "$Author", Likes : {$max : "$Likes"}}}])
{ "_id" : "B. R. Bhagwat", "Likes" : 150 }
{ "_id" : "J. K. Rowling", "Likes" : 75 }

> db.book.aggregate([{$group : {_id : "$Author", "Average Pages" : {$avg : "$Pages"}}}])
{ "_id" : "J. K. Rowling", "Average Pages" : 250 }
{ "_id" : "B. R. Bhagwat", "Average Pages" : 200 }


> db.book.aggregate([{$group : {_id : "$Author", Likes : {$max : "$Likes"}, Cost: {$avg : "$Cost"}}}])
{ "_id" : "J. K. Rowling", "Likes" : 75, "Cost" : 167.75 }
{ "_id" : "B. R. Bhagwat", "Likes" : 150, "Cost" : 211 }

```

### Pipeline
