
ACC.birberberApiService = {
    get: function (url, params) {
        return $.get(url, params);
    }, post: function (url, params) {
        return $.post(url, params);
    }
}

ACC.birberber = {
    el: {
        showMarketsByIlceBtn: 'svg path, svg text', updateGoalsBtn: '.update-goals'
    }, onInit: function () {
        this.events.mapEvent();
        this.events.updateGoalsEvent();
    }, events: {
        mapEvent: function () {
            var _ucz = ACC.birberber;
            $(document).on("click", _ucz.el.showMarketsByIlceBtn, function () {
                var _t = $(this);
                var _id = _t.data('id');
                var params = {'ilceId': _id}
                _ucz.methods.showMarketsByIlce(params);
            });
        }, updateGoalsEvent: function () {
            var _ucz = ACC.birberber;
            $(document).on("click", _ucz.el.updateGoalsBtn, function () {
                _ucz.methods.updateGoals();
            });
        }
    }, methods: {
        showMarketsByIlce: function (params) {
            var _ucz = ACC.birberber;
            _ucz.services.showMarketsByIlce(params).promise().done(function (res) {
                _ucz.methods.renderTable(res);
            })
        }, updateGoals: function () {
            var _ucz = ACC.birberber;
            _ucz.services.updateAllGoals().promise().done(function (res) {
                alert(ACC.message.goals.updated);
                window.location.reload();
            })
        }, renderTable: function (res) {
            $('#myModal .modal-body').html(res);
            $('#myModal').modal('toggle');
        }
    }, services: {
        showMarketsByIlce: function (params) {
            var url = '/stores';
            return ACC.birberberApiService.get(url, params);
        }, updateAllGoals: function () {
            var url = '/update-goals';
            return ACC.birberberApiService.get(url);
        }
    }
};

$(document).ready(function () {
    with (ACC.birberber) {
        onInit();
    }
});