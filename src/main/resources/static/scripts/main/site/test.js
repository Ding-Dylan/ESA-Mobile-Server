(function (window, undefined) {
    var PopupLogin = Base.getClass('main.component.PopupLogin');

    Base.ready({
        initialize: fInitialize
    });

    function fInitialize() {
        PopupLogin.show({
            listeners: {
                login: function () {
                    alert('Login');
                },
                register: function () {
                    alert('Register');
                }
            }
        });
    }

})(window);