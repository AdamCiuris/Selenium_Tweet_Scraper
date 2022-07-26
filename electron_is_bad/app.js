const { spawn } = require('child_process');
var chgBtn = document.getElementById('spawn-process');
var netstat;

function replaceText(selector, text){
    const element = document.getElementById(selector);
    if (element) element.innerText = text;
}    

chgBtn.onclick = function(e) {
    netstat= spawn('java', ['-jar', '../target/selesecondgo-1.0-SNAPSHOT.jar', '2']);
    netstat.stdout.on('data', (data) => {
        console.log(`stdout: ${data}`);
      });
      
      netstat.stderr.on('data', (data) => {
        console.error(`stderr: ${data}`);
      });
      
      netstat.on('close', (code) => {
        console.log(`child process exited with code ${code}`);
      });
      
}



