const fs = require('fs');
const stringSimilarity = require('string-similarity');
const testFolder = '../Data_Images';
const writeFolder = '../Distances/';
let data= [];
console.log("-----Starting-----");
let i = 0;

var temp = fs.readdirSync(testFolder);

temp.forEach(file => {
  if (!file.includes("png") && !file.includes("Book1")) {
    console.log("File:  " + file);
    var content = require(testFolder + "/" + file);
    data[i] = content;
    i++;
    console.log("----------");
  };
});
console.log("-----Parsing ends-----");
console.log("data: " + data[0].description);
let obj = {};
console.log("--------Calculation starts-------");
data.forEach(parent => {
  obj[parent.id] = [];
  data.forEach(child => {
    if (parent.id != child.id && parent.description && child.description) {
      const distance = stringSimilarity.compareTwoStrings(parent.description, child.description);
      obj[parent.id].push({
        id: child.id,
        distance: distance
      })
    }
  })
});
console.log("--------Calculation ends-------");
fs.writeFile(writeFolder + "/test.json", JSON.stringify(obj), function(err) {
    if(err) {
        return console.log(err);
    }

    console.log("The file was saved!");
});
// console.log(JSON.stringify(obj));
