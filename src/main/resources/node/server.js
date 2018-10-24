const fs = require('fs');
const stringSimilarity = require('string-similarity');
const testFolder = '../Data_Images';
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
    if (parent.id != child.id) {
      const distance = stringSimilarity.compareTwoStrings(parent.description, child.description);
      obj[parent.id].push({
        id: child.id,
        distance: distance
      })
    }
  })
});
console.log("--------Calculation ends-------");
console.log(JSON.stringify(obj));
