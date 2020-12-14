function refreshCanvas(id, beginTime, endTime, heatingTime, coolingTime, periodsQuantity) {
    var canvas = document.getElementById(id);

    var width = canvas.width;
    var height = 100;
    var scale = width / 1440;
    var timelineLevel = 80;
    var heatingLineLevel = 10;

    var ctx = canvas.getContext("2d");
    ctx.setLineDash([]);
    ctx.strokeStyle = "black";
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    ctx.strokeStyle = "#FF0000";
    if (beginTime) {
        ctx.beginPath();
        ctx.moveTo(beginTime / scale, timelineLevel + 6);
        ctx.lineTo(beginTime / scale, timelineLevel);
        ctx.stroke();
    }

    if (endTime) {
        ctx.beginPath();
        ctx.moveTo(endTime / scale, timelineLevel + 6);
        ctx.lineTo(endTime / scale, timelineLevel);
        ctx.stroke();
    }

    ctx.strokeStyle = "black";
    ctx.beginPath();
    ctx.moveTo(0, timelineLevel);
    ctx.lineTo(beginTime / scale, timelineLevel);
    ctx.stroke();
    ctx.strokeStyle = "red";
    ctx.beginPath();
    ctx.moveTo(beginTime / scale, timelineLevel);
    var x = beginTime / scale;
    for (i = 0; i < periodsQuantity; i++) {
        ctx.lineTo(x, heatingLineLevel);
        x = x + heatingTime / (scale * 60);
        ctx.lineTo(x, heatingLineLevel);
        ctx.lineTo(x, timelineLevel);
        x = x + coolingTime / (scale * 60);
        ctx.lineTo(x, timelineLevel);
    }
    ctx.stroke();

    ctx.strokeStyle = "black";
    ctx.beginPath();
    ctx.moveTo(endTime / scale, timelineLevel);
    ctx.lineTo(width, timelineLevel);
    ctx.stroke();
    ctx.font = "12px Arial";
    //ctx.fillText("0:00", 1 , 98);
    ctx.fillText(secondsToTime(beginTime), beginTime / scale - 15, 98);
    ctx.fillText(secondsToTime(endTime), endTime / scale - 15, 98);
    //ctx.fillText("24:00", width-30, 98);

    var today = new Date();
    var timeNow = today.getHours() * 60 + today.getMinutes();

    ctx.strokeStyle = "red";
    ctx.beginPath();
    ctx.setLineDash([5, 5]);
    ctx.moveTo(timeNow / scale, 0);
    ctx.lineTo(timeNow, height);
    ctx.stroke();
}

function secondsToTime(minutes) {
    var hour = Math.floor(minutes / 60);
    if (hour < 10) {
        hour = "0" + hour;
    }
    var min = minutes % 60;
    if (min < 10) {
        min = "0" + min;
    }
    return "" + hour + ":" + min;
}

function getAnchor(timerName, href) {
    var anchor = document.createElement("a");
    anchor.setAttribute("href", href);
    var text = document.createTextNode(timerName);
    anchor.appendChild(text);
    return anchor;
}

function renderTable(timers) {
    timers.forEach(element => {
        var trNode = document.createElement("tr");
        var nameNode = document.createElement("td");
        nameNode.classList.add("name");
        nameNode.appendChild(getAnchor(element.name, "/edit?id=" + element.id));
        nameNode.appendChild(getAnchor("  Delete timer", "javascript:deleteTimer(" + element.id + ");"));
        var canvasNode = document.createElement("canvas");
        canvasNode.id = element.id + element.name + "Canvas";
        canvasNode.width = 1440;
        canvasNode.height = 100;
        canvasNode.style.border = "1px solid #c3c3c3";
        trNode.appendChild(nameNode);
        trNode.appendChild(canvasNode);
        document.getElementById("timersTable").appendChild(trNode);
    });
}

function renderGraph(timer, id) {
    if (!id) {
      var id = timer.id + timer.name + "Canvas";
    }
    var beginTime = timer.beginTime.hour * 60 + timer.beginTime.minute;
    var endTime = timer.endTime.hour * 60 + timer.endTime.minute;

    var totalTime = (endTime - beginTime) * 60;
    var heatingTime = timer.powerConsumption * 3600 / (timer.heatingPower * timer.periodsQuantity);
    var coolingTime = (totalTime / timer.periodsQuantity) - heatingTime;

    refreshCanvas(id, beginTime, endTime, heatingTime, coolingTime, timer.periodsQuantity);
}

function renderMainPage(timers) {
    renderTable(timers);
    timers.forEach(timer => {
        renderGraph(timer);
    });
}

function getTimers() {
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4) {
            renderMainPage(JSON.parse(xhr.responseText));
        }
    };
    xhr.open('GET', '/timer');
    xhr.send();
}

function deleteTimer(id) {
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4) {
            location.reload();
        }
    };
    xhr.open('DELETE', '/timer/' + id);
    xhr.send();
}

function getTimer() {
    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);
    const id = urlParams.get('id')

    if (id) {
        var xhr = new XMLHttpRequest();
        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4) {
                var timer = JSON.parse(xhr.responseText)
                setValues(timer)
                renderGraph(timer, "canvas");
            }
        };
        xhr.open('GET', '/timer/' + id);
        xhr.send();
    }
}

function setValue(id, value) {
  document.getElementById(id).value = value;
}

function setValues(timer) {
  setValue("id", timer.id);
  setValue("name", timer.name);
  //"beginTime":{"minute":0,"hour":9,"second":0},"endTime":{"minute":55,"hour":23,"second":0},
  setValue("beginTime", timer.beginTime.hour+":"+timer.beginTime.minute);
  setValue("endTime", timer.endTime.hour+":"+timer.endTime.minute);
  setValue("powerConsumption", timer.powerConsumption);
  setValue("periodsQuantity", timer.periodsQuantity);
  setValue("heatingPower", timer.heatingPower);
  setValue("controllerHostname", timer.controllerHostname);
}