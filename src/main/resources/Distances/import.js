
db.dropDatabase();

let file = cat('./DescriptionDistance.json');
var data = JSON.parse(file);
db.description.insert(data);
