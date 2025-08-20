// Notification Updater Script
$(document).ready(function() {
    // Update notification count every 30 seconds
    setInterval(updateNotificationCount, 30000);
    
    // Update immediately when page loads
    updateNotificationCount();
});

function updateNotificationCount() {
    $.ajax({
        url: 'NotificationServlet',
        type: 'GET',
        data: {
            service: 'getUnreadCount'
        },
        dataType: 'json',
        success: function(data) {
            // Update notification count in header
            $('.notifications-menu .label').text(data.count);
            
            // Add pulse effect if there are new notifications
            if (data.count > 0) {
                $('.notifications-menu .label').addClass('pulse');
            } else {
                $('.notifications-menu .label').removeClass('pulse');
            }
        },
        error: function(xhr, status, error) {
            console.log('Error updating notification count: ' + error);
        }
    });
}

// Add CSS for pulse animation effect
$('<style>')
    .prop('type', 'text/css')
    .html(`
        .pulse {
            animation: pulse 2s infinite;
        }
        @keyframes pulse {
            0% {
                transform: scale(1);
            }
            50% {
                transform: scale(1.1);
            }
            100% {
                transform: scale(1);
            }
        }
    `)
    .appendTo('head'); 