document.addEventListener('DOMContentLoaded', () => {
    const userId = '664eaeaa6380437538d4523c'; // Substitute with the actual user ID
    const profileImage = document.getElementById('profileImage');
    const userName = document.getElementById('userName');
    const userEmail = document.getElementById('userEmail');
    const imageUpload = document.getElementById('imageUpload');
    const uploadButton = document.getElementById('uploadButton');
    const message = document.getElementById('message');

    // Function to load user info and profile image
    const loadUserProfile = () => {
        fetch(`http://localhost:8080/users/${userId}`)
            .then(response => response.json())
            .then(user => {
                userName.textContent = user.name;
                userEmail.textContent = user.email;
                // Add more user info if needed

                return fetch(`http://localhost:8080/users/${userId}/image`);
            })
            .then(response => {
                if (response.ok) {
                    return response.blob();
                } else {
                    throw new Error('No image found');
                }
            })
            .then(imageBlob => {
                const imageUrl = URL.createObjectURL(imageBlob);
                profileImage.src = imageUrl;
            })
            .catch(error => {
                console.error('Error loading user profile:', error);
            });
    };

    // Function to upload a new profile image
    const uploadProfileImage = () => {
        const file = imageUpload.files[0];
        if (!file) {
            message.textContent = 'Please select an image to upload.';
            return;
        }

        const formData = new FormData();
        formData.append('image', file);

        fetch(`http://localhost:8080/users/${userId}/uploadImage`, {
            method: 'POST',
            body: formData
        })
            .then(response => {
                if (response.ok) {
                    message.textContent = 'Image uploaded successfully!';
                    loadUserProfile();
                } else {
                    throw new Error('Failed to upload image');
                }
            })
            .catch(error => {
                console.error('Error uploading image:', error);
                message.textContent = 'Error uploading image.';
            });
    };

    // Load user profile on page load
    loadUserProfile();

    // Add event listener to upload button
    uploadButton.addEventListener('click', uploadProfileImage);
});

