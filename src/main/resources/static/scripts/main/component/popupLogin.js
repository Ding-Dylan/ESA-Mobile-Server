(function (window) {
    var PopupLogin = Base.createClass('main.component.PopupLogin');
    var Popup = Base.getClass('main.component.Popup');
    var Component = Base.getClass('main.component.Component');
    var Util = Base.getClass('main.base.Util');

    Base.mix(PopupLogin, Component, {
        _tpl: [
            '<div class="wrapper-content clearfix">',
                '<div class="input-section">',
                    '<div class="form-group">',
                        '<label class="control-label">Mail address</label>',
                        '<div class="control-group js-email"><input type="email" placeholder="Please enter mail address"></div>',
                    '</div>',
                    '<div class="form-group">',
                        '<label class="control-label">Password</label>',
                        '<div class="control-group js-pwd"><input type="password" placeholder="Please enter password"></div>',
                    '</div>',
                    '<div class="form-group about-pwd">',
                        '<div class="keep-pwd">',
                            '<label><input type="checkbox" class="js-remember"> Remember me</label>',
                        '</div>',
                    '</div>',
                    '<div class="form-group">',
                        '<div class="col-input-login">',
                            '<a class="btn btn-info js-login" href="javascript:void(0);">Log In</a>',
                            '<a class="btn btn-info js-register" href="javascript:void(0);">Register</a>',
                        '</div>',
                    '</div>',
                '</div>',
            '</div>'].join(''),
        listeners: [{
            name: 'render',
            type: 'custom',
            handler: function () {
                var that = this;
                var oEl = that.getEl();
                that.emailIpt = oEl.find('div.js-email');
                that.pwdIpt = oEl.find('div.js-pwd');
                that.initCpn();
            }
        }, {
            name: 'click a.js-login',
            handler: function (oEvent) {
                oEvent.preventDefault();
                var that = this;
                // 值检查
                if (!that.checkVal()) {
                    return;
                }
                var oData = that.val();
                $.ajax({
                    url: '/login/',
                    type: 'post',
                    dataType: 'json',
                    data: {
                        username: oData.email,
                        password: oData.pwd,
                        remember: oData.remember ? 1 : 0
                    }
                }).done(function (oResult) {
                    if (oResult.code === 0) {
//                        window.location.reload();
                        that.emit('login');
                    } else {
                        oResult.msgname && that.iptError(that.emailIpt, oResult.msgname);
                        oResult.msgpwd && that.iptError(that.pwdIpt, oResult.msgpwd);
                    }
                }).fail(function () {
                    alert('出现错误，请重试');
                });
            }
        }, {
            name: 'click a.js-register',
            handler: function (oEvent) {
                oEvent.preventDefault();
                var that = this;
                // 值检查
                if (!that.checkVal()) {
                    return;
                }
                var oData = that.val();
                $.ajax({
                    url: '/reg/',
                    type: 'post',
                    dataType: 'json',
                    data: {
                        username: oData.email,
                        password: oData.pwd
                    }
                }).done(function (oResult) {
                    if (oResult.code === 0) {
//                        window.location.reload();
                        that.emit('register');
                    } else {
                        oResult.msgname && that.iptError(that.emailIpt, oResult.msgname);
                        oResult.msgpwd && that.iptError(that.pwdIpt, oResult.msgpwd);
                    }
                }).fail(function () {
                    alert('出现错误，请重试');
                });
            }
        }],
        show: fStaticShow
    }, {
        initialize: fInitialize,
        initCpn: fInitCpn,
        val: fVal,
        checkVal: fCheckVal,
        iptSucc: fIptSucc,
        iptError: fIptError,
        iptNone: fIptNone
    });

    function fStaticShow(oConf) {
        var that = this;
        var oLogin = new PopupLogin(oConf);
        var oPopup = new Popup({
            width: 540,
            content: oLogin.html()
        });
        oLogin._popup = oPopup;
        Component.setEvents();
    }

    function fInitialize(oConf) {
        var that = this;
        delete oConf.renderTo;
        PopupLogin.superClass.initialize.apply(that, arguments);
    }

    function fInitCpn() {
        var that = this;
        that.emailIpt.find('input').on('focus', Base.bind(that.iptNone, that, that.emailIpt));
        that.pwdIpt.find('input').on('focus', Base.bind(that.iptNone, that, that.pwdIpt));
    }

    function fVal(oData) {
        var that = this;
        var oEl = that.getEl();
        var oEmailIpt = that.emailIpt.find('input');
        var oPwdIpt = that.pwdIpt.find('input');
        var oRememberChk = oEl.find('.js-remember');
        if (arguments.length === 0) {
            return {
                email: $.trim(oEmailIpt.val()),
                pwd: $.trim(oPwdIpt.val()),
                remember: oRememberChk.prop('checked')
            };
        } else {
            oEmailIpt.val($.trim(oData.email));
            oPwdIpt.val($.trim(oData.pwd));
            oRememberChk.prop('checked', !!oData.remember);
        }
    }

    function fCheckVal() {
        var that = this;
        var oData = that.val();
        var bRight = true;
        /*
        if (!Util.isEmail(oData.email)) {
            that.iptError(that.emailIpt, 'Please fill in the correct email address.');
            bRight = false;
        }*/
        if (!oData.pwd) {
            that.iptError(that.pwdIpt, 'Password cannot be null.');
            bRight = false;
        } else if (oData.pwd.length < 6) {
            that.iptError(that.pwdIpt, 'Password cannot be less than 6 digits.');
            bRight = false;
        }
        return bRight;
    }

    function fIptSucc(oIpt) {
        var that = this;
        oIpt = $(oIpt);
        that.iptNone(oIpt);
        oIpt.addClass('success');
        if (!oIpt.find('.icon-ok-sign').get(0)) {
            oIpt.append('<i class="input-icon icon-ok-sign"></i>');
        }
    }
    
    function fIptError(oIpt, sMsg) {
        var that = this;
        oIpt = $(oIpt);
        that.iptNone(oIpt);
        oIpt.addClass('error');
        if (!oIpt.find('.icon-remove-sign').get(0)) {
            oIpt.append('<i class="input-icon icon-remove-sign"></i>');
        }
        var oSpan = oIpt.find('.input-tip');
        if (!oSpan.get(0)) {
            oSpan = $('<span class="input-tip"></span>');
            oIpt.append(oSpan);
        }
        oSpan.html($.trim(sMsg));
    }

    function fIptNone(oIpt) {
        var that = this;
        $(oIpt).removeClass('error success');
    }
})(window);