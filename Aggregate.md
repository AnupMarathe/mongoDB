## Aggregation
```
> db.book.find({})
{ "_id" : ObjectId("62b47bb86240fd26d5ae76b5"), "title" : "Harry Potter 1", "Author" : "J. K. Rowling", "Pages" : 100 }
{ "_id" : ObjectId("62b47bb86240fd26d5ae76b6"), "title" : "Harry Potter 2", "Author" : "J. K. Rowling", "Pages" : 200 }
{ "_id" : ObjectId("62b47bb86240fd26d5ae76b7"), "title" : "Harry Potter 3", "Author" : "J. K. Rowling", "Pages" : 300 }
{ "_id" : ObjectId("62b47bb86240fd26d5ae76b8"), "title" : "Harry Potter 4", "Author" : "J. K. Rowling", "Pages" : 400 }
{ "_id" : ObjectId("62b47bb86240fd26d5ae76b9"), "title" : "Faster Fene 1", "Author" : "B. R. Bhagwat", "Pages" : 150 }
{ "_id" : ObjectId("62b47bb86240fd26d5ae76ba"), "title" : "Faster Fene 2", "Author" : "B. R. Bhagwat", "Pages" : 250 }
> db.book.aggregate([{$group : {_id : "$Author", num_books : {$sum : 1}}}])
{ "_id" : "J. K. Rowling", "num_books" : 4 }
{ "_id" : "B. R. Bhagwat", "num_books" : 2 }
```
