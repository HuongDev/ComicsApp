/*
RESTFul Services by NodeJS
Author : Huong
Update : 11/01/2019
*/

var express = require('express');
var mysql = require('mysql');
var bodyParser = require('body-parser')

// connect to MySQL
var con =  mysql.createConnection({
	host: "localhost",
	user: "root",
	password: "",
	database: "testmanga" // Name of datebase you just import
});

// Create RESTFul
var app = express();
var publicDir = (__dirname+'/public/'); // Set static dir for display image local by url
app.use(express.static(publicDir));
app.use(bodyParser.json())
app.use(bodyParser.urlencoded({extended: true}));

con.connect(function(err) {
  if (err) throw err;
  console.log("Connected!");
});

//GET all banner
app.get("/banner", (req,res,next)=>{
	con.query("SELECT * FROM banner", function (err, result, fields) {
		con.on("error", function(error) {
			console.log('[MY SQL ERROR] ' + error);
		});

		if (err) {
		res.end(JSON.stringify("No Comic here"));	
		}
		else
		{
			res.end(JSON.stringify(result));
		}
	})
});


//GET all comic
app.get("/comic", (req,res,next)=>{
	con.query("SELECT * FROM manga", function (err, result, fields) {
		con.on("error", function(error) {
			console.log('[MY SQL ERROR] ' + error);
		});

		if (err) {
		res.end(JSON.stringify("No Comic here"));	
		}
		else
		{
			res.end(JSON.stringify(result));
		}
	})
});



//GET Chapter by manga ID
app.get("/chapter/:mangaid", (req,res,next)=>{
	con.query("SELECT * FROM chapter where MangaID=?",[req.params.mangaid], function (err, result, fields) {
		con.on("error", function(error) {
			console.log('[MY SQL ERROR] ' + error);
		});

		if (err) {
			res.end(JSON.stringify("No chapter here"));	
			console.log('[MY SQL ERROR] ' + err);
		}
		else
		{
			res.end(JSON.stringify(result));
			console.log('[MY SQL ERROR] ' + err);
		}
	})
});

//GET Images by manga ID
app.get("/link/:chapterid", (req,res,next)=>{
	con.query("SELECT * FROM link where ChapterID=?",[req.params.chapterid], function (err, result, fields) {
		con.on("error", function(error) {
			console.log('[MY SQL ERROR] ' + error);
		});

		if (err) {
			res.end(JSON.stringify("No chapter here"));	
			console.log('[MY SQL ERROR] ' + err);
		}
		else
		{
			res.end(JSON.stringify(result));
			console.log('[MY SQL ERROR] ' + err);
		}
	})
});



//GET All Categories
// app.get("/categories", (req,res,next)=>{
// 	con.query("SELECT * FROM category", function (err, result, fields) {
// 		con.on("error", function(error) {
// 			console.log('[MY SQL ERROR] ' + err);
// 		});

// 		if (err) {
// 			res.end(JSON.stringify("No category here"));	
// 			console.log('[MY SQL ERROR] ' + err);
// 		}
// 		else
// 		{
// 			res.end(JSON.stringify(result));
// 			console.log('[MY SQL ERROR] ' + err);
// 		}
// 	})
// });

app.get("/categories", (req,res,next)=>{

	con.on("error", function(error) {
			console.log('[MY SQL ERROR] ' + error);
		});

	con.query("SELECT * FROM category", function (err, result, fields) {
		    if (err) throw err;
		    console.log(result);
		    res.end(JSON.stringify(result));
		  });
});



//GET All Comic by multiple categories
app.post("/filter", (req,res,next)=>{
	var post_data = req.body;
	var array = JSON.parse(post_data.data);
	var query = "SELECT * FROM manga WHERE ID IN (SELECT MangaID FROM mangacategory";

	if (array.length > 0) {
		query += " GROUP BY MangaID";
		if(array.length == 1)
			query += " HAVING SUM(CASE WHEN CategoryID = "+array[0]+" THEN 1 ELSE 0 END) > 0 )";
		else
		{
			for(var i = 0; i < array.length; i++)
			{
				if (i == 0) 
					query += " HAVING SUM(CASE WHEN CategoryID="+array[0]+" THEN 1 ELSE 0 END) > 0 AND";
				
				else if (i == array.length-1)
					query += " SUM(CASE WHEN CategoryID="+array[i]+" THEN 1 ELSE 0 END) > 0)";
				
				else
					query += " SUM(CASE WHEN CategoryID="+array[i]+" THEN 1 ELSE 0 END) > 0 AND";
			}
		}
	}
	con.query(query, function (err, result, fields) {
		con.on("error", function(error) {
			console.log('[ERROR] ' + error);
		});

		if (err) {
			res.end(JSON.stringify("No comic here"));	
			console.log('[MY SQL ERROR] ' + err);
		}
		else
		{
			res.end(JSON.stringify(result));
			console.log('[Success] ' + JSON.stringify(result));
		}
	})
});


//SEARCH COMIC BY NAME
app.post("/search", (req,res,next)=>{
	var post_data = req.body;
	var name_search = post_data.search;
	var query = "SELECT * FROM manga WHERE Name LIKE '%" + name_search +"%'";

	con.query(query, function (err, result, fields) {
		con.on("error", function(error) {
			console.log('[MY SQL ERROR] ' + error);
		});

		if (err) {
			res.end(JSON.stringify("No comic here"));	
			console.log('[MY SQL ERROR] ' + err);
		}
		else
		{
			res.end(JSON.stringify(result));
			console.log('[Success] ' + JSON.stringify(result));
		}
	})
});

// app.get("/test", (req,res,next)=>{

// con.query("SELECT * FROM manga", function (err, result, fields) {
//     if (err) throw err;
//     console.log(JSON.stringify(result));

//     res.end(JSON.stringify(result));
//   });key=AAAAnRJ55R4:APA91bHYjBSjnllPG67jme666e-Eo-T3R9b5py
// });


//Start Server
app.listen(3000, ()=>{
	console.log('Comic API running on port 3000');
})