$(document).ready(function() {

    const apiUrl = '/a2aw-api/usuario';
    const $mensagem = $('#mensagem');

    $('#form-cadastro').submit(function(event) {
        event.preventDefault();

        const dadosUsuario = {
            nome: $('#nome').val(),
            email: $('#email').val(),
            idade: parseInt($('#idade').val())
        };

        $.ajax({
            type: 'POST',
            url: apiUrl,
            contentType: 'application/json',
            data: JSON.stringify(dadosUsuario),
            
            success: function(response) {
                $mensagem.text('Usuário "' + response.nome + '" cadastrado com sucesso! ID: ' + response.id);
                $mensagem.removeClass('msg-erro').addClass('msg-sucesso');
                $mensagem.show();
                
                $('#form-cadastro')[0].reset();
            },
            error: function(xhr, status, error) {
                let erroMsg = 'Erro desconhecido. Tente novamente.';
                
                if (xhr.responseText) {
                    erroMsg = xhr.responseText;
                }
                
                $mensagem.text(erroMsg);
                $mensagem.removeClass('msg-sucesso').addClass('msg-erro');
                $mensagem.show();
            }
        });
    });
});