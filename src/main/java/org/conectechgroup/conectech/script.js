document.getElementById('uploadForm').addEventListener('submit', function(event) {
    event.preventDefault();

    const userId = document.getElementById('userId').value;
    const imageFile = document.getElementById('imageFile').files[0];

    if (!userId || !imageFile) {
        document.getElementById('message').textContent = "Please provide a user ID and select an image file.";
        return;
    }

    const formData = new FormData();
    formData.append('image', imageFile);

    fetch(`http://localhost:8080/users/${userId}/uploadImage`, {
        method: 'POST',
        body: formData
    })
        .then(response => response.text())
        .then(data => {
            document.getElementById('message').textContent = data;
            loadImage(userId);
        })
        .catch(error => {
            document.getElementById('message').textContent = "Error uploading image.";
            console.error('Error:', error);
        });
});

function loadImage(userId) {
    fetch(`http://localhost:8080/users/${userId}/image`)
        .then(response => response.blob())
        .then(blob => {
            const imgElement = document.createElement('img');
            imgElement.src = URL.createObjectURL(blob);
            imgElement.alt = "User Image";
            imgElement.style.maxWidth = "100%";
            imgElement.style.height = "auto";

            const imageContainer = document.getElementById('imageContainer');
            imageContainer.innerHTML = "";
            imageContainer.appendChild(imgElement);
        })
        .catch(error => {
            document.getElementById('message').textContent = "Error loading image.";
            console.error('Error:', error);
        });
}
