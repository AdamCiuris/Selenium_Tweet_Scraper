const { app, BrowserWindow, Menu} = require("electron")
const path = require('path')
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
    if (process.platform !== "darwin") app.quit();
})

