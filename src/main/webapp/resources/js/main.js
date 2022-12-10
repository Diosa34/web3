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

function setArea(checkbox) {
    if (!checkbox.checked) {
        document.getElementById("g"+checkbox.id[6]).innerHTML = '';
    } else {
        addArea(checkbox.id[6]);
    }
}

function checkboxClick(checkbox){
    setArea(checkbox);
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


function drawPoint(svg, x, y){
    svg.innerHTML += "<circle cx='" + x + "' cy='" + y + "' r='7' fill='black'/>";
}


let x_elem = document.getElementById("x")
let y_elem = document.getElementById("y-inputHidden")
let submit_elem = document.getElementById("submit")
function formClick(x, y){
    x_elem.value = x;
    y_elem.value = y;
    submit_elem.click();
}