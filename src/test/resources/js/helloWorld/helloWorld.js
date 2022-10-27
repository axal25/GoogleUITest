var element = document.getElementById("bodyId");
setTimeout(() => {
    var tag = document.createElement("span");
    tag.appendChild(document.createTextNode("Hello world span."));
    tag.setAttribute('id', 'spanId');
    element.appendChild(tag);
}, 2500);
