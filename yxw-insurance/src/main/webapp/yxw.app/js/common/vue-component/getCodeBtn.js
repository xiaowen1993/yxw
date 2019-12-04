define([ 'vue', ], function (Vue) {

    /**
     * 2017-11-29 14:27:43
     * 获取验证码
     * by nick
     */

    Vue.component("codeBtn", {
        template: '<button class="btn"   type="button"  @click="send" :disabled="disabled">{{label}}</button>',
        props: [
            'disabled', 'value', 'url', 'name'
            
        ],
        data() {
            return {
                label: "发送验证码",
                sending: false,
                t: 0
            }
        },
        computed: {
            disabled() {
                let isPhone = /^1[3-8]\d{9}$/.test(this.value);
                if (this.sending) {
                    return true
                } else {
                    if (isPhone) {
                        return false
                    } else {
                        return true
                    }

                }
            }
        },
        methods: {
            send() {
                let v = this.value,
                    vm = this;
                let name = this.name || 'phone';
                let data = {}
                data[name] = v;             
                if (v && this.url) {
              
                    $.ajax({
                        url: vm.url,
                        data: data,
                        success: function (res) {
                            vm.lastTime();
                            console.log(res)
                            if (res.code) {
                          
                                vm.$emit('success','res')

                            }

                        }
                    })
                }else{
                    alert('url或value不能为空')
                }
            },
            lastTime() {
                let s = 59,
                    vm = this;
                this.label = '60s重发';
                vm.sending = true;
                clearInterval(vm.t)
                vm.t = setInterval(res => {
                    if (s <= 0) {
                        vm.label = '发送验证码'
                        vm.sending = false
                        s = 59;
                        clearInterval(vm.t)
                    } else {
                        vm.label = (s < 10 ? '0' + s : s) + 's 重发'
                        s--;

                    }


                }, 1000)

            }


        }
    })

});