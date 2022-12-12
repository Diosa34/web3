function addArea(r) {
    let numberR = Number(r)
    let areaFill = 810 - numberR
        document.getElementById("g"+r).innerHTML += `<path id="path${r}" 
              d="M 480 480
              L ${480-40*numberR} 480
              L 480 ${480-80*numberR}
              A ${80*numberR} ${80*numberR} 0 0 1 ${480+80*numberR} 480
              L ${480+40*numberR} 480
              L ${480+40*r} ${480+80*numberR}
              L 480 ${480+80*numberR}
              L 480 480" stroke="black" fill="#fc${areaFill}f""/>`
}

function checkboxClick(checkbox){
    if (!checkbox.checked) {
        document.getElementById("g"+checkbox.id[6]).innerHTML = '';
    } else {
        addArea(checkbox.id[6]);
        drawPointsFromDB(checkbox.id[6])
    }
}

function svgClick(event, svg, r) {
    let svgCoord = svg.getBoundingClientRect() // DOMRect object

    let xPartOfSvg = (event.clientX - svgCoord.x)/svgCoord.width // координата(в долях) клика относительно svg
    let yPartOfSvg = (event.clientY - svgCoord.y)/svgCoord.height
    drawPoint(svg, (xPartOfSvg) * 960, (yPartOfSvg) * 960)

    let x = (xPartOfSvg - 0.5) * 3 * r
    let y = -1 * (yPartOfSvg - 0.5) * 3 * r
    formClick(x, y)
}
// когда кликают по свг, то точки отрисовываются на свг, но точки из бд отрисовываются в группах, при чём при каждой смене радиуса
function drawPoint(svgOrG, x, y, resultFill='black'){
    svgOrG.innerHTML += `<circle cx="${x}" cy="${y}" r='7' fill="${resultFill}"/>`;
}


let x_elem = document.getElementById("x")
let y_elem = document.getElementById("y-inputHidden")
let submit_elem = document.getElementById("submit")
function formClick(x, y){
    x_elem.value = x;
    y_elem.value = y;
    submit_elem.click();
}

let pointsList = document.querySelectorAll(".result-table td")

function drawPointsFromDB(r){
    for (let i = 10; i < pointsList.length; i += 10) {
        if (pointsList[i+2+r].innerHTML === "true") {
            let result = pointsList[i+8].innerHTML;
            let x = Number(pointsList[i+1]);
            let y = Number(pointsList[i+2]);
            let fill = "FireBrick";
            if (result.startsWith("In")) {
                fill = "MediumAquamarine";
            }
            if (x <= 960 && y <= 960) {
                drawPoint("g"+i, x, y, fill)
            }
        }
    }
//строчка для извлечения радиусов, в которые точка попала let rList = result.substring(result.indexOf(":") + 2, result.length()).split(" ");
}
