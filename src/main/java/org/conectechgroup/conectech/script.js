$(document).ready(function() {
    $('#addUserForm').submit(function(event) {
        event.preventDefault(); // Impede o envio padrão do formulário

        // Obter dados do formulário
        const userData = {
            name: $('#name').val(),
            email: $('#email').val(),
            dateOfBirth: $('#dateOfBirth').val(),
            password: $('#password').val(),
            cpfcnpj: $('#cpfcnpj').val()
        };

        // Enviar solicitação POST usando AJAX
        $.ajax({
            url: "http://localhost:8080/users",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify(userData),
            success: function(response) {
                console.log("Usuário adicionado com sucesso:", response);
                // Limpar o formulário após adicionar o usuário
                $('#addUserForm')[0].reset();
                // Atualizar a interface do usuário conforme necessário
            },
            error: function(xhr, status, error) {
                console.error("Erro ao adicionar usuário:", status, error);
                // Exibir uma mensagem de erro na interface do usuário, se necessário
            }
        });
    });
});
