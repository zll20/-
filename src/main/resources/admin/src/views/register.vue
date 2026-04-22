<template>
	<div>
		<div class="register-container" :style="{'backgroundImage': indexBgUrl?`url(${$base.url + indexBgUrl})`:''}">
			<el-form v-if="pageFlag=='register'" ref="ruleForm" class="rgs-form animate__animated animate__" :model="ruleForm" :rules="rules">
				<div class="rgs-form2">
					<div class="title">词途记英语学习平台</div>
					<el-form-item class="list-item" v-if="tableName=='xuesheng'">
						<div class="lable" :class="changeRules('xuehao')?'required':''">学号：</div>
						<el-input  v-model="ruleForm.xuehao" :readonly="ro.xuehao" autocomplete="off" placeholder="学号"  type="text"  />
					</el-form-item>
					<el-form-item class="list-item" v-if="tableName=='xuesheng'">
						<div class="lable" :class="changeRules('mima')?'required':''">密码：</div>
						<el-input  v-model="ruleForm.mima" :readonly="ro.mima" autocomplete="off" placeholder="密码"  type="password"  />
					</el-form-item>
					<el-form-item class="list-item" v-if="tableName=='xuesheng'">
						<div class="lable" :class="changeRules('mima')?'required':''">确认密码：</div>
						<el-input  v-model="ruleForm.mima2" autocomplete="off" placeholder="确认密码" type="password" :readonly="ro.mima" />
					</el-form-item>
					<el-form-item class="list-item" v-if="tableName=='xuesheng'">
						<div class="lable" :class="changeRules('xingming')?'required':''">姓名：</div>
						<el-input  v-model="ruleForm.xingming" :readonly="ro.xingming" autocomplete="off" placeholder="姓名"  type="text"  />
					</el-form-item>
					<el-form-item class="list-item" v-if="tableName=='xuesheng'">
						<div class="lable" :class="changeRules('xingbie')?'required':''">性别：</div>
						<el-select filterable v-model="ruleForm.xingbie" placeholder="请选择性别" :disabled="ro.xingbie">
							<el-option
								v-for="(item,index) in xueshengxingbieOptions"
								v-bind:key="index"
								:label="item"
								:value="item">
							</el-option>
						</el-select>
					</el-form-item>
					<el-form-item class="list-item" v-if="tableName=='xuesheng'">
						<div class="lable" :class="changeRules('touxiang')?'required':''">头像：</div>
						<file-upload
							tip="点击上传头像"
							action="file/upload"
							:limit="3"
							:multiple="true"
							:fileUrls="ruleForm.touxiang?ruleForm.touxiang:''"
							@change="xueshengtouxiangUploadChange"
						></file-upload>
					</el-form-item>
					<el-form-item class="list-item" v-if="tableName=='xuesheng'">
						<div class="lable" :class="changeRules('youxiang')?'required':''">邮箱：</div>
						<el-input  v-model="ruleForm.youxiang" :readonly="ro.youxiang" autocomplete="off" placeholder="邮箱"  type="text"  />
					</el-form-item>
					<el-form-item class="list-item email" v-if="tableName=='xuesheng'">
						<div class="lable" :class="changeRules('mobile')?'required':''">手机号码：</div>
						<div style="display: flex;flex: 1;">
							<input v-model="ruleForm.mobile" autocomplete="off" placeholder="手机号"/>
							<button v-if="isEndFlag" type="success" class="register-code" @click="sendsmscode()">发送验证码</button>
							<button v-if="!isEndFlag" type="success" class="register-code" disabled="disabled">{{SecondToDate}}</button>
						</div>
					</el-form-item>
					<el-form-item class="list-item email-code" v-if="tableName=='xuesheng'">
						<div class="lable" :class="changeRules('mobile')?'required':''">验证码：</div>
						<el-input  v-model="smscode" autocomplete="off" placeholder="验证码" />
					</el-form-item>
					<el-form-item class="list-item" v-if="tableName=='xuesheng'">
						<div class="lable" :class="changeRules('pquestion')?'required':''">密保问题：</div>
						<el-input  v-model="ruleForm.pquestion" :readonly="ro.pquestion" autocomplete="off" placeholder="密保问题"  type="text"  />
					</el-form-item>
					<el-form-item class="list-item" v-if="tableName=='xuesheng'">
						<div class="lable" :class="changeRules('panswer')?'required':''">密保答案：</div>
						<el-input  v-model="ruleForm.panswer" :readonly="ro.panswer" autocomplete="off" placeholder="密保答案"  type="text"  />
					</el-form-item>
					<div class="register-btn">
						<div class="register-btn1">
							<button type="button" class="r-btn" @click="login()">注册</button>
						</div>
						<div class="register-btn2">
							<div class="r-login" @click="close()">取消</div>
						</div>
					</div>
				</div>
			</el-form>
		</div>
	</div>
</template>

<script>
	import 'animate.css'
export default {
	data() {
		return {
			ruleForm: {
			},
			forgetForm: {},
            pageFlag : '',
			tableName:"",
			rules: {},
			ro: {},
            smscode:'',
			// 倒计时时间
			count: 60,
			// 倒计时定时器
			inter: null,
			// 倒计时是否结束
			isEndFlag: true,
            xueshengxingbieOptions: [],
			indexBgUrl: '',
		};
	},
	computed: {
		SecondToDate: function() {
			var time = this.count;
			if (null != time && "" != time) {
				time = parseInt(time) + "秒后重发";
			}
			return time;
		}
	},
	mounted(){
		this.pageFlag = this.$route.query.pageFlag
		if(this.$route.query.pageFlag=='register'){
			
			let table = this.$storage.get("loginTable");
			this.tableName = table;
			if(this.tableName=='xuesheng'){
				this.ruleForm = {
					xuehao: '',
					mima: '',
					xingming: '',
					xingbie: '',
					touxiang: '',
					youxiang: '',
					mobile: '',
					pquestion: '',
					panswer: '',
				}
				this.ro = {
					xuehao: false,
					mima: false,
					xingming: false,
					xingbie: false,
					touxiang: false,
					youxiang: false,
					mobile: false,
					pquestion: false,
					panswer: false,
				}
			}
			this.xueshengxingbieOptions = "男,女".split(',')
		}
	},
	created() {
		this.$http.get('config/info?name=bRegisterBackgroundImg',).then(rs=>{this.indexBgUrl = rs.data.data?rs.data.data.value:''})
	},
	destroyed() {
		  	},
	methods: {
		changeRules(name){
			if(this.rules[name]){
				return true
			}
			if(name == 'pquestion'||name=='panswer') {
				return true
			}
			return false
		},
		// 获取uuid
		getUUID () {
			return new Date().getTime();
		},
		close(){
			this.$router.push({ path: "/login" });
		},
        xueshengtouxiangUploadChange(fileUrls) {
            this.ruleForm.touxiang = fileUrls;
        },

        // 多级联动参数

		sendsmscode() {
			if(!this.ruleForm.mobile){
				this.$message.error(`手机号码不能为空`);
				return
			}
			if(this.ruleForm.mobile&&(!this.$validate.isMobile(this.ruleForm.mobile))){
				this.$message.error(`请输入正确的手机格式`);
				return
			}
			this.isEndFlag = false;
			var _this = this;
			this.inter = window.setInterval(function() {
				_this.count = _this.count - 1;
				if (_this.count <= 0) {
					window.clearInterval(_this.inter);
					_this.isEndFlag = true;
					_this.count=60;
				}
			}, 1000);
			this.$http({
				url: `${this.tableName}/sendsms?mobile=`+this.ruleForm.mobile,
				method: "get",
				params: {}
			}).then(({ data }) => {
				if (data && data.code === 0) {
					this.$message.success(`发送成功`);
				} else {
					this.$message.error(data.msg)
				}
			});
		},

		// 注册
		login() {
			var url=this.tableName+"/register";
			if((this.ruleForm.mima!=this.ruleForm.mima2) && `xuesheng` == this.tableName){
				this.$message.error(`两次密码输入不一致`);
				return
			}
            if(this.ruleForm.touxiang!=null) {
                this.ruleForm.touxiang = this.ruleForm.touxiang.replace(new RegExp(this.$base.url,"g"),"");
            }
			if(`xuesheng` == this.tableName && this.ruleForm.youxiang &&(!this.$validate.isEmail(this.ruleForm.youxiang))){
				this.$message.error(`邮箱应输入邮件格式`);
				return
			}
			if(`xuesheng` == this.tableName && this.ruleForm.mobile &&(!this.$validate.isMobile(this.ruleForm.mobile))){
				this.$message.error(`手机号应输入手机格式`);
				return
			}
			if(`xuesheng` == this.tableName ){
				url=this.tableName+"/register?smscode="+this.smscode;
				if(this.ruleForm.mobile&&(!this.$validate.isMobile(this.ruleForm.mobile))){
					this.$message.error(`请输入正确的手机格式`);
					return
				}
			}
			if((!this.smscode) && `xuesheng` == this.tableName){
				this.$message.error(`验证码不能为空`);
				return
			}
			if(`xuesheng` == this.tableName && this.ruleForm.pquestion==''){
				this.$message.error(`密保问题不能为空`);
				return
			}
			if(`xuesheng` == this.tableName && this.ruleForm.panswer==''){
				this.$message.error(`密保答案不能为空`);
				return
			}
			this.$http({
				url: url,
				method: "post",
				data:this.ruleForm
			}).then(({ data }) => {
				if (data && data.code === 0) {
					this.$message({
						message: "注册成功",
						type: "success",
						duration: 1500,
						onClose: () => {
							this.$router.replace({ path: "/login" });
						}
					});
				} else {
					this.$message.error(data.msg);
				}
			});
		},
	}
};
</script>

<style lang="scss" scoped>
.register-container {
	position: relative;
	background: url(http://codegen.caihongy.cn/20260116/390f7f9db4ee4d609e06f01761503028.jpg) no-repeat  center  center / 100% 100%!important;
	background: url(http://codegen.caihongy.cn/20260116/390f7f9db4ee4d609e06f01761503028.jpg) no-repeat  center  center / 100% 100%!important;
	display: flex;
	width: 100%;
	min-height: 100vh;
	justify-content: center;
	align-items: flex-end;
	position: relative !important;
	.rgs-form {
		.rgs-form2 {
		padding: 31px 86px 88px 86px;
		margin: 209px  auto  0 auto;
		align-content: center;
		background: url(http://codegen.caihongy.cn/20250825/017111e89a2d4bdca280e362defd821f.png) no-repeat  center center /  100% 100%;
		display: flex;
		width: 1038px;
		align-items: center;
		flex-wrap: wrap;
		}
		border-radius: 0;
		padding: 0;
		box-shadow: inset 0px 0px 0px 0px #000;
		margin: 0 auto;
		z-index: 1000;
		display: flex;
		width: 100%;
		min-height: 100vh;
		justify-content: center;
		flex-wrap: wrap;
		height: auto;
		.title {
			padding: 15px 0 30px;
			margin: 0 0 0px 0;
			color: #E56B00;
			top: 4%;
			left: 29%;
			font-weight: 600;
			width: 42%;
			font-size: 26px;
			line-height: 40px;
			position: absolute;
			text-align: center;
		}
		.list-item {
			border-radius: 30px;
			padding: 0 0 0 130px;
			margin: 14px 0;
			background: none;
			width:  100%;
			position: relative;
			height: auto;
			::v-deep .el-form-item__content {
				display: block;
			}
			.lable {
				padding: 0 10px 0 0;
				color: #333;
				left: -130px;
				width: 130px;
				font-size: 16px;
				line-height: 44px;
				position: absolute !important;
				text-align: right;
			}
			.el-input {
				width: 100%;
			}
			.el-input ::v-deep .el-input__inner {
				border: 1px solid #DADFE6;
				border-radius: 0px;
				padding: 0 10px;
				box-shadow:  inset 0px 3px 6px 1px rgba(0,0,0,0.16);
				color: #333;
				background: #fff;
				width: 100%;
				font-size: 16px;
				height: 63px;
			}
			.el-input ::v-deep .el-input__inner:focus {
				border: 1px solid #DADFE6;
				border-radius: 0px;
				padding: 0 10px;
				box-shadow:  inset 0px 3px 6px 1px rgba(0,0,0,0.16);
				color: #333;
				background: #fff;
				width: 100%;
				font-size: 16px;
				height: 63px;
			}
			.el-input-number {
				width: 100%;
			}
			.el-input-number ::v-deep .el-input__inner {
				text-align: center;
				border: 1px solid #DADFE6;
				border-radius: 0px;
				padding: 0 10px;
				box-shadow:  inset 0px 3px 6px 1px rgba(0,0,0,0.16);
				color: #333;
				background: #fff;
				width: 100%;
				font-size: 16px;
				height: 63px;
			}
			.el-input-number ::v-deep .el-input__inner:focus {
				border: 1px solid #DADFE6;
				border-radius: 0px;
				padding: 0 10px;
				box-shadow:  inset 0px 3px 6px 1px rgba(0,0,0,0.16);
				color: #333;
				background: #fff;
				width: 100%;
				font-size: 16px;
				height: 63px;
			}
			.el-input-number ::v-deep .el-input-number__decrease {
				display: none;
			}
			.el-input-number ::v-deep .el-input-number__increase {
				display: none;
			}
			.el-select {
				width: 100%;
			}
			.el-select ::v-deep .el-input__inner {
				border: 1px solid #DADFE6;
				border-radius: 0px;
				padding: 0 10px;
				box-shadow:  inset 0px 3px 6px 1px rgba(0,0,0,0.16);
				color: #333;
				background: #fff;
				width: 100%;
				font-size: 16px;
				height: 63px;
			}
			.el-select ::v-deep .el-input__inner:focus {
				border: 1px solid #DADFE6;
				border-radius: 0px;
				padding: 0 10px;
				box-shadow:  inset 0px 3px 6px 1px rgba(0,0,0,0.16);
				color: #333;
				background: #fff;
				width: 100%;
				font-size: 16px;
				height: 63px;
			}
			.el-date-editor {
				width: 100%;
			}
			.el-date-editor ::v-deep .el-input__inner {
				border: 1px solid #DADFE6;
				border-radius: 0px;
				padding: 0 30px;
				box-shadow:  inset 0px 3px 6px 1px rgba(0,0,0,0.16);
				color: #333;
				background: #fff;
				width: 100%;
				font-size: 16px;
				height: 63px;
			}
			.el-date-editor ::v-deep .el-input__inner:focus {
				border: 1px solid #DADFE6;
				border-radius: 0px;
				padding: 0 30px;
				box-shadow:  inset 0px 3px 6px 1px rgba(0,0,0,0.16);
				color: #333;
				background: #fff;
				width: 100%;
				font-size: 16px;
				height: 63px;
			}
			.el-date-editor.el-input {
				width: 100%;
			}
			::v-deep .el-upload--picture-card {
				background: transparent;
				border: 0;
				border-radius: 0;
				width: auto;
				height: auto;
				line-height: initial;
				vertical-align: middle;
			}
			::v-deep .upload .upload-img {
				border: 1px solid #DADFE6;
				cursor: pointer;
				border-radius: 0px;
				margin: 5px 0 0;
				color: #999;
				background: #fff;
				width: 90px;
				font-size: 26px;
				line-height: 60px;
				text-align: center;
				height: 60px;
			}
			::v-deep .el-upload-list .el-upload-list__item {
				border: 1px solid #DADFE6;
				cursor: pointer;
				border-radius: 0px;
				margin: 5px 0 0;
				color: #999;
				background: #fff;
				width: 90px;
				font-size: 26px;
				line-height: 60px;
				text-align: center;
				height: 60px;
			}
			::v-deep .el-upload .el-icon-plus {
				border: 1px solid #DADFE6;
				cursor: pointer;
				border-radius: 0px;
				margin: 5px 0 0;
				color: #999;
				background: #fff;
				width: 90px;
				font-size: 26px;
				line-height: 60px;
				text-align: center;
				height: 60px;
			}
			::v-deep .el-upload__tip {
				color: #666;
				font-size: 16px;
			}
			::v-deep .el-input__inner::placeholder {
				color: #123;
				font-size: 16px;
			}
			.required {
				position: relative;
			}
			.required::after{
				color: red;
				left: 120px;
				position: absolute;
				content: "*";
			}
			.editor {
				border: 1px solid #DADFE6;
				border-radius: 4px;
				box-shadow: inset 0px 3px 6px 1px rgba(0,0,0,0.16);
				background: #fff;
				width: 100%;
				height: auto;
			}
			.editor>.avatar-uploader {
				line-height: 0;
				height: 0;
			}
		}
		.list-item.email {
			input {
				border: 1px solid #DADFE6;
				border-radius: 0px;
				padding: 0 30px;
				box-shadow:  inset 0px 3px 6px 1px rgba(0,0,0,0.16);
				color: #333;
				background: #fff;
				width: 100%;
				font-size: 16px;
				height: 63px;
			}
			input:focus {
				border: 1px solid #DADFE6;
				border-radius: 0px;
				padding: 0 30px;
				box-shadow:  inset 0px 3px 6px 1px rgba(0,0,0,0.16);
				color: #333;
				background: #fff;
				width: 100%;
				font-size: 16px;
				height: 63px;
			}
			input::placeholder {
				color: #123;
				font-size: 16px;
			}
			button {
				border: 0;
				cursor: pointer;
				padding: 0 0px;
				margin: 0;
				color: #FF7700;
				font-size: 16px;
				border-color: #acacac;
				border-radius: 0 0px 0px 0;
				box-shadow: none;
				outline: none;
				background: #FFEAD8;
				width: 180px;
				border-width: 0;
				border-style: solid;
				height: 64px;
			}
			button:hover {
				color: #4c7bf5;
				border-color: #acacac;
			}
		}
		.register-btn {
			padding: 0 0 0 130px;
			display: flex;
			width: 100%;
			justify-content: center;
		}
		.register-btn1 {
			padding: 0;
			margin: 0 50px 0 0;
			width: auto;
			text-align: center;
		}
		.register-btn2 {
			padding: 0;
			margin: 0;
			width: auto;
			text-align: center;
		}
		.r-btn {
			border: 0;
			cursor: pointer;
			border-radius: 24px;
			padding: 0 20px;
			margin: 10px auto 10px auto;
			outline: none;
			color: #FF7700;
			background: #FFEAD8;
			font-weight: 400;
			width: 176px;
			font-size: 16px;
			height: 47px;
		}
		.r-btn:hover {
			opacity: 0.5;
		}
		.r-login {
			border: 0;
			cursor: pointer;
			padding: 0 20px;
			margin: 10px auto;
			color: #666666;
			font-weight: 400;
			font-size: 16px;
			line-height: 47px;
			border-radius: 24px;
			outline: none;
			background: #FFEAD8;
			width: 176px;
			height: 47px;
		}
		.r-login:hover {
			opacity: 0.8;
		}
	}
}
	
	::-webkit-scrollbar {
	  display: none;
	}
</style>

