$(document).ready(function() {
    
    const apiUrl = '/a2aw-api/usuario';
    let listaDeUsuarios = []; 

    const $tabelaCorpo = $('#tabela-corpo');
    const $contadorUsuarios = $('#contador-usuarios');
    const $mensagem = $('#mensagem');
    const $campoBusca = $('#campo-busca');
    const $modalEditar = $('#modal-editar');
    const $modalRemover = $('#modal-remover');
    
    let idUsuarioParaRemover = null;

    function desenharTabela(usuarios) {
        $tabelaCorpo.empty();
        
        if (usuarios.length === 0) {
            $contadorUsuarios.text('Nenhum usuário cadastrado.');
            $tabelaCorpo.html('<tr><td colspan="5" style="text-align:center;">Nenhum usuário encontrado.</td></tr>');
            return;
        }

        $contadorUsuarios.text(`Mostrando ${usuarios.length} usuário(s)`);
        
        usuarios.forEach(usuario => {
            const linhaHtml = `
                <tr>
                    <td data-label="ID">${usuario.id}</td>
                    <td data-label="Nome">${usuario.nome}</td>
                    <td data-label="E-mail">${usuario.email}</td>
                    <td data-label="Idade">${usuario.idade}</td>
                    <td class="td-acoes">
                        <button class="btn-acao btn-editar" data-id="${usuario.id}">
                            <i class="fas fa-pencil-alt"></i>
                        </button>
                        <button class="btn-acao btn-remover" data-id="${usuario.id}" data-nome="${usuario.nome}">
                            <i class="fas fa-trash-alt"></i>
                        </button>
                    </td>
                </tr>
            `;
            $tabelaCorpo.append(linhaHtml);
        });
    }

    function controlarModal(idModal, mostrar) {
        const $modal = $(`#${idModal}`);
        if (mostrar) {
            $modal.css('display', 'flex');
        } else {
            $modal.hide();
        }
    }

    function mostrarMensagem(texto, tipo) {
        $mensagem.text(texto);
        $mensagem.removeClass('msg-sucesso msg-erro').addClass(`msg-${tipo}`);
        $mensagem.show();
        setTimeout(() => { $mensagem.hide(); }, 3500);
    }

    function carregarUsuarios() {
        $.ajax({
            type: 'GET',
            url: apiUrl,
            success: function(response) {
                listaDeUsuarios = response;
                desenharTabela(listaDeUsuarios);
            },
            error: function() {
                mostrarMensagem('Falha ao carregar usuários da API.', 'erro');
                $contadorUsuarios.text('Falha ao carregar.');
            }
        });
    }

    $campoBusca.on('keyup', function() {
        const termo = $(this).val().toLowerCase();
        if (termo === '') {
            desenharTabela(listaDeUsuarios);
        } else {
            const usuariosFiltrados = listaDeUsuarios.filter(u => 
                u.nome.toLowerCase().includes(termo) ||
                u.email.toLowerCase().includes(termo)
            );
            desenharTabela(usuariosFiltrados);
        }
    });

    $tabelaCorpo.on('click', '.btn-editar', function() {
        const id = $(this).data('id');
        const usuario = listaDeUsuarios.find(u => u.id === id);
        
        if (usuario) {
            $('#edit-id').val(usuario.id);
            $('#edit-nome').val(usuario.nome);
            $('#edit-email').val(usuario.email);
            $('#edit-idade').val(usuario.idade);
            controlarModal('modal-editar', true);
        }
    });

    $('#form-modal-editar').submit(function(event) {
        event.preventDefault();
       
        const usuarioAtualizado = {
            id: $('#edit-id').val(),
            nome: $('#edit-nome').val(),
            email: $('#edit-email').val(),
            idade: parseInt($('#edit-idade').val())
        };

        $.ajax({
            type: 'PUT',
            url: apiUrl,
            contentType: 'application/json',
            data: JSON.stringify(usuarioAtualizado),
            success: function() {
                const index = listaDeUsuarios.findIndex(u => u.id === usuarioAtualizado.id);
                listaDeUsuarios[index] = usuarioAtualizado;
                
                $campoBusca.trigger('keyup'); 
                controlarModal('modal-editar', false);
                mostrarMensagem('Usuário atualizado com sucesso.', 'sucesso');
            },
            
            error: function(xhr, status, error) {
                let erroMsg = 'Falha ao atualizar usuário.';
                if (xhr.responseText) {
                    erroMsg = xhr.responseText;
                }
                
                mostrarMensagem(erroMsg, 'erro');
            }
        });
    });

    $tabelaCorpo.on('click', '.btn-remover', function() {
        idUsuarioParaRemover = $(this).data('id');
        const nome = $(this).data('nome');
        
        $('#confirmar-remocao-texto').html(`Tem certeza que deseja remover o usuário <strong>${nome}</strong>?`);
        controlarModal('modal-remover', true);
    });

    $('#btn-confirmar-remocao').click(function() {
        $.ajax({
            type: 'DELETE',
            url: `${apiUrl}/${idUsuarioParaRemover}`,
            success: function() {
                listaDeUsuarios = listaDeUsuarios.filter(u => u.id !== idUsuarioParaRemover);
                
                $campoBusca.trigger('keyup');
                controlarModal('modal-remover', false);
                mostrarMensagem('Usuário removido com sucesso.', 'sucesso');
            },
            error: function() {
                mostrarMensagem('Falha ao remover usuário.', 'erro');
            }
        });
    });

    $('.modal-fechar, .btn-cancelar').click(function() {
        const modalId = $(this).data('modal');
        controlarModal(modalId, false);
    });

    carregarUsuarios();
});