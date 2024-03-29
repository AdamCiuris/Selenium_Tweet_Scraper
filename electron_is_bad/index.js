const { app, BrowserWindow, Menu} = require("electron")
const path = require('path')
const { spawn } = require('child_process');
// Electron combines the Chromium rendering engine and the Nodejs runtime.


const createWindow = () => {
    const win = new BrowserWindow({
        height: 500,
        width: 800,
        webPreferences: {
            nodeIntegration: true,  // needed for require
            contextIsolation: false, // needed for require
            preload: path.join(__dirname, 'preload.js'),            
        }
        
    })
    win.webContents.openDevTools();

    win.loadFile('index.html');
}

app.whenReady().then(() => {
    createWindow();

})

app.on("window-all-close", () => {
    if (process.platform !== "darwin")  {
        spawn('taskkill', ['/IM', 'chromedriver.exe', '/F']); // force chromedriver to die, I don't know how to naturally end it.
        // without above chromedriver.exe becomes a zombie process

        app.quit();
    }
})

