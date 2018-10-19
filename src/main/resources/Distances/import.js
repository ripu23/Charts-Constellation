
db.dropDatabase();

let file = cat('./DescriptionDistance.json');
const data = JSON.parse(file);
db.description.insert(data);
