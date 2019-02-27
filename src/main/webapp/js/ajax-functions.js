function encodeFormData(data) {
    if (!data) return '';
    if (typeof data !== 'object') return '';
    var encodedData = '';
    for (field in data) {
        if (!data.hasOwnProperty(field)) continue;
        encodedData += '&' + encodeURIComponent(field)
            + '=' + encodeURIComponent(data[field]);
    }
    return encodedData.slice(1);
}

function ajaxPost(url, data, callback) {
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {

        if (this.readyState == 4 && this.status == 200) {
            callback(xhr.responseText, xhr);
        }
    };
    xhr.open('POST', url);
    xhr.setRequestHeader('X-Requested-With', 'XMLHttpRequest');
    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    xhr.setRequestHeader('Accept', 'application/json');
    xhr.send(encodeFormData(data));
}