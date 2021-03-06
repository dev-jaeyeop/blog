'use strict';

function _write_comment(board_id) {
    const content = document.getElementById('write_comment_content_form').value;

    axios
        .post(`/api/boards/${board_id}/comments`, {
            content,
        })
        .then(() => window.location.reload())
        .catch((error) => alert(error.response.data.message));
}

function _edit_comment(comment_id) {
    const content = document.getElementById(
        `edit_comment_content_form_${comment_id}`
    ).value;

    axios
        .put(`/api/comments/${comment_id}`, {
            content,
        })
        .then(() => window.location.reload())
        .catch((error) => alert(error.response.data.message));
}

function _delete_comment(comment_id) {
    axios
        .delete(`/api/comments/${comment_id}`)
        .then(() => window.location.reload())
        .catch((error) => alert(error.response.data.message));
}

function _show_edit_comment_buttons(comment_id) {
    const edit_comment_content_form = document.getElementById(
        `edit_comment_content_form_${comment_id}`
    );
    const edit_comment = document.getElementsByClassName(`edit_comment_${comment_id}`);

    edit_comment_content_form.style.border = '1px solid rgba(0, 0, 0, 0.1)';
    edit_comment_content_form.style.backgroundColor = 'revert';
    edit_comment_content_form.toggleAttribute('readOnly');
    Array.from(edit_comment).forEach(element => element.toggleAttribute('hidden'))
}

function _show_delete_comment_buttons(comment_id) {
    const delete_comment_button = document.getElementById(
        'delete_comment_button'
    );
    delete_comment_button.setAttribute(
        'onClick',
        `_delete_comment(${comment_id})`
    );
}
