// Функция jQuery, которая собирает данные из формы и посылает их аяксом на сервер
$(document).ready(function() {
    $('.delete-task-button').click(function() {
        var $taskID = $(this).data("id");
        $.ajax({
            url: 'api/deleteTask',
            type: 'GET',
            data: {
                'id': $taskID
            },
            success: function() {
                alert("Может быть ваша задача была удалена. А может и нет...");
            }
        });
    });
    $('#add-task-button').click(function() {
        // Получаем значения из полей
        var $taskTitle = $('#task-title').val();
        var $taskParentList = $('#task-parent').val();
        var $targetDate = $('#task-target-date').val();
        var $taskPriority = $('#task-priority').val();
        var $taskContent = $('#task-content').val();

        // Task fields: id author list title content creationTime = new Date(); targetTime completed=false; priority
        $.ajax({
            url: 'api/addTask',
            type: 'POST',
            data: {
                'list': $taskParentList,
                'title': $taskTitle,
                'content': $taskContent,
                'targetTime': $targetDate,
                'priority': $taskPriority
            },
            success: function(data) {
                alert("Задача добавлена! Может быть...");
            }
        });
    });
});