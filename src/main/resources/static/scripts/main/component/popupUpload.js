/**
var oPopupUpload = new PopupUpload({
    
});
 */
(function (window) {
    var PopupUpload = Base.createClass('main.component.PopupUpload');
    var Popup = Base.getClass('main.component.Popup');
    var Upload = Base.getClass('main.component.Upload');
    var Component = Base.getClass('main.component.Component');
    var Util = Base.getClass('main.base.Util');

    Base.mix(PopupUpload, Component, {
        _tpl: [
            '<div>',
              '<div class="form-group">',
                '<div class="form-group">',
                    '<label class="col-sm-2 control-label">Upload Image</label>',
                    '<div class="js-image-container col-sm-10">',
                        '<a href="javascript:void(0);" class="btn btn-info btn-upload js-upload-btn" style="diplay:inline-block;position:relative;">Upload image</a>',
                    '</div>',
                '</div>',
                  '<div class="form-group"><label class="col-sm-2 control-label">Title</label><div class="col-sm-10"><input class="js-title form-control" type="text"></div></div>',
                  '<div class="form-group"><label class="col-sm-2 control-label">Link</label><div class="col-sm-10"><input class="js-link form-control" type="text"></div></div>',
                  '<div class="form-group">',
                        '<div class="col-lg-10 col-lg-offset-2">',
                            '<input type="submit" value="Commit" class="js-submit btn btn-default btn-info">',
                        '</div>',
                    '</div>',
            '</div>'].join(''),
        listeners: [{
            name: 'render',
            type: 'custom',
            handler: function () {
                var that = this;
                var oEl = that.getEl();
                var oUploadBtn = oEl.find('a.js-upload-btn');
                new Upload({
                    targetEl: oUploadBtn,
                    url: '/uploadImage/',
                    check: function (oFile, sType, nFileSize) {
                        var sMsg = nFileSize === 0 ? 'File size cannot be 0.' : /image/gi.test(sType || '') ? '' : 'File format is incorrect.';
                        sMsg && alert(sMsg);
                        return !sMsg;
                    },
                    call: function (oResult) {
                        var sUrl = $.trim(oResult.msg);
                        if (oResult.code !== 0) {
                            return alert('An error occurred, please try again.');
                        }
                        that.image = sUrl;
                        that.showImage(sUrl);
                    }
                });
            }
        }, {
            name: 'click input.js-submit',
            handler: function () {
                var that = this;
                var oEl = that.getEl();
                var sTitle = $.trim(oEl.find('input.js-title').val());
                var sLink = $.trim(oEl.find('input.js-link').val());
                if (!sTitle) {
                    return alert('Title cannot be null.');
                }
                if (!sLink) {
                    return alert('Link cannot be null.');
                }
                if (!that.image) {
                    return alert('Image cannot be null.');
                }
                if (that.requesting) {
                    return;
                }
                that.requesting = true;
                $.ajax({
                    url: '/user/addNews/',
                    method: 'post',
                    data: {image: that.image, title: sTitle, link: sLink},
                    dataType: 'json'
                }).done(function (oResult) {
                    that.emit('done');
                }).fail(function (oResult) {
                    alert('An error occurred, please try again.');
                }).always(function () {
                    that.requesting = false;
                });
            }
        }],
        show: fStaticShow
    }, {
        initialize: fInitialize,
        showImage: fShowImage
    });

    function fStaticShow(oConf) {
        var that = this;
        var oLogin = new PopupUpload(oConf);
        var oPopup = new Popup({
            title: 'Share',
            width: 700,
            content: oLogin.html()
        });
        oLogin._popup = oPopup;
        Component.setEvents();
    }

    function fInitialize(oConf) {
        var that = this;
        delete oConf.renderTo;
        PopupUpload.superClass.initialize.apply(that, arguments);
    }

    function fShowImage(sUrl) {
        var that = this;
        var oEl = that.getEl();
        var sHtml = [
            '<div class="letter-pic-box">',
                '<a href="javascript:void(0);" class="icon-remove-circle"></a>',
                '<div class="mask"></div>',
                '<img src="' + sUrl + '">',
            '</div>'].join('');
        oEl.find('div.letter-pic-box').remove();
        oEl.find('div.js-image-container').prepend(sHtml);
    }

})(window);