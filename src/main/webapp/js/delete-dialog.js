/**
 * A modal dialog for a delete action
 */

$(document).on("click", ".open-DeleteDialog", function() {
    var endpoint = $(this).data('id');
    $(".modal-footer #deleteBtn").html('<a href="#" class="btn" data-dismiss="modal" aria-hidden="true">Cancel</a> <a href="' + endpoint +'" class="btn btn-danger">Delete</a>');
   }
);