function drawGraphic(checkbox, svg_element){
    if (!checkbox.checked) {
        svg_element.style.display = "none";
    } else {
        svg_element.style.display = "inline-block";
    }
}


function svgClick(event, svg, r) {
    let svgCoord = svg.getBoundingClientRect() // DOMRect object

    let xPartOfSvg = (event.clientX - svgCoord.x)/svgCoord.width // координата(в долях) клика относительно svg
    let yPartOfSvg = (event.clientY - svgCoord.y)/svgCoord.height
    drawPoint(svg, (xPartOfSvg) * 600, (yPartOfSvg) * 600)

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