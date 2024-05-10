const { readFileSync } = require("fs");
const readline = require('readline');
const fs = require('fs'); // Importa el módulo fs

const insrc = readline.createInterface({
  input: process.stdin,
  output: process.stdout
});

entrada = [];
i = 0; 

async function readInput(n){
    var line;
for await (line of insrc) {
    entrada.push(parseInt(line));
    n--;
    if (n==0) return;
    }
    insrc.close();
}

var importObjects = {
    runtime: {
        exceptionHandler : function(code) {
            let errStr;
            if (code == 1) {
                errStr= "Error 1. ";
            } else if (code == 2) {
                errStr= "Error 2. ";
        } else if (code == 3) {
                errStr= "Not enough memory. ";
        } else {
        errStr= "Unknown error\n";
            }
            throw new Error(errStr);
        },
    print: function(n) {
        fs.appendFileSync("./launch/src/webAssembly/output.txt", n + "\n");
        },
    read: function() {
        let val = entrada[i];
        i += 1;
        return val;
        }
    }};

async function start() {
    fs.writeFileSync("./launch/src/webAssembly/output.txt", "");
    const code = fs.readFileSync("./launch/src/webAssembly/Main.wasm");
    const wasmModule = await WebAssembly.compile(code);
    const instance = await WebAssembly.instantiate(wasmModule, importObjects);
    process.exit(0)
}

async function run() {
    // await readInput(2);
    start();
}

run();