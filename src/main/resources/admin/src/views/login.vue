<template>
	<div>
		<div class="login-container" :style="{'backgroundImage': indexBgUrl?`url(${$base.url + indexBgUrl})`:''}">
			<el-form class="login_form animate__animated animate__">
				<div class="login_form2">
					<div class="title-container">词途记英语学习平台登录</div>
					<div v-if="loginType==1" class="list-item">
						<input placeholder="请输入账号：" name="username" type="text" v-model="rulesForm.username">
					</div>
					<div v-if="loginType==1" class="list-item">
						<div class="password-box">
							<input placeholder="请输入密码：" name="password" :type="showPassword?'text':'password'" v-model="rulesForm.password">
							<span class="icon iconfont" :class="showPassword?'icon-liulan13':'icon-liulan17'" @click="showPassword=!showPassword"></span>
						</div>
					</div>

					<div class="list-item select" v-if="roles.length>1&&loginType<=2">
						<el-select v-model="rulesForm.role" placeholder="请选择角色：">
							<el-option v-if="loginType==1||(loginType==2&&item.role!='users')" v-for="item in roles" :key="item.roleName" :label="item.roleName" :value="item.roleName" />
						</el-select>
					</div>

		
					<div class="login-btn">
						<div class="login-btn1">
							<el-button v-if="loginType==1||loginType==3||loginType==4" type="primary" @click="login()" class="loginInBt">登录</el-button>
						</div>
						<div class="login-btn2">
						</div>
						<div class="login-btn3">
						</div>
					</div>
				</div>
			</el-form>
		</div>
	</div>
</template>
<script>
	import 'animate.css'
	import menu from "@/utils/menu";
	export default {
		data() {
			return {
				verifyCheck2: false,
				flag: false,
				baseUrl:this.$base.url,
				loginType: 1,
				rulesForm: {
					username: "",
					password: "",
					role: "",
				},
				menus: [],
				roles: [],
				tableName: "",
				showPassword: false,
				indexBgUrl: '',
			};
		},
		mounted() {
			let menus = menu.list();
			this.menus = menus;

			for (let i = 0; i < this.menus.length; i++) {
				if (this.menus[i].hasBackLogin=='是') {
					this.roles.push(this.menus[i])
				}
			}

		},
		created() {
			this.$http.get('config/info?name=bLoginBackgroundImg',).then(rs=>{this.indexBgUrl = rs.data.data?rs.data.data.value:''})
		},
		destroyed() {
		},
		components: {
		},
		methods: {

			//注册
			register(tableName){
				this.$storage.set("loginTable", tableName);
				this.$router.push({path:'/register',query:{pageFlag:'register'}})
			},
			// 登陆
			login() {
				if(this.loginType==1) {

					if (!this.rulesForm.username) {
						this.$message.error("请输入用户名");
						return;
					}
					if (!this.rulesForm.password) {
						this.$message.error("请输入密码");
						return;
					}
					if(this.roles.length>1) {
						if (!this.rulesForm.role) {
							this.$message.error("请选择角色");
							return;
						}
					
						for (let i = 0; i < this.roles.length; i++) {
							if (this.roles[i].roleName == this.rulesForm.role) {
								this.tableName = this.roles[i].tableName;
							}
						}
					} else {
						this.tableName = this.roles[0].tableName;
						this.rulesForm.role = this.roles[0].roleName;
					}
				}
		
				this.loginPost()
			},
			loginPost() {
				this.$http({
					url: `${this.tableName}/login?username=${this.rulesForm.username}&password=${this.rulesForm.password}`,
					method: "post"
				}).then(({ data }) => {
					if (data && data.code === 0) {
						this.$storage.set("Token", data.token);
						this.$storage.set("role", this.rulesForm.role);
						this.$storage.set("sessionTable", this.tableName);
						this.$storage.set("adminName", this.rulesForm.username);
						this.$nextTick(()=>{
							this.$http({
								url: this.tableName + '/session',
								method: "get"
							}).then(({
								data
							}) => {
								if (data && data.code === 0) {
									if(this.tableName == 'xuesheng') {
										this.$storage.set('examName',data.data.xingming)
									}
									if(this.tableName == 'xuesheng') {
										this.$storage.set('headportrait',data.data.touxiang)
									}
									if(this.tableName == 'users') {
										this.$storage.set('headportrait',data.data.image)
									}
									this.$storage.set('userForm',JSON.stringify(data.data))
									this.$storage.set('userid',data.data.id);
								} else {
									let message = this.$message
									message.error(data.msg);
								}
								this.$router.replace({ path: "/" });
							});
						})
					}
					else {
						this.$message.error(data.msg);
					}
				});
			},
		}
	}
</script>

<style lang="scss" scoped>
.login-container {
	min-height: 100vh;
	position: relative;
	background-repeat: no-repeat;
	background-position: center center;
	background-size: cover;
	background: url(http://codegen.caihongy.cn/20260116/390f7f9db4ee4d609e06f01761503028.jpg) no-repeat  center  center / 100% 100%!important;
	background: url(http://codegen.caihongy.cn/20260116/390f7f9db4ee4d609e06f01761503028.jpg) no-repeat  center  center / 100% 100%!important;
	display: flex;
	width: 100%;
	min-height: 100vh;
	justify-content: center;
	align-items: flex-end;
	position: relative !important;

	.login_form {
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
		.login_form2 {
			padding: 50px 87px 88px 87px ;
			margin: auto ;
			align-content: center;
			background: url(http://codegen.caihongy.cn/20250825/017111e89a2d4bdca280e362defd821f.png) no-repeat  center center /  100% 100%;
			display: flex;
			width: 738px;
			align-items: center;
			flex-wrap: wrap;
		}
		.title-container {
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
			padding: 0 0px;
			margin: 10px 0;
			background: none;
			display: flex;
			width: 100%;
			align-items: center;
			input {
				border: 1px solid #DADFE6;
				padding: 0 10px;
				color: #333;
				font-size: 16px;
				border-color: #acacac;
				border-radius: 0px;
				box-shadow:  inset 0px 3px 6px 1px rgba(0,0,0,0.16);
				background: #fff;
				flex: 1;
				width: 100%;
				border-width: 0 0 2px;
				border-style: solid;
				height: 64px;
			}
			input:focus {
				border: 1px solid #DADFE6;
				padding: 0 10px;
				color: #333;
				font-size: 16px;
				border-color: #acacac;
				border-radius: 0px;
				box-shadow:  inset 0px 3px 6px 1px rgba(0,0,0,0.16);
				background: #fff;
				flex: 1;
				width: 100%;
				border-width: 0 0 2px;
				border-style: solid;
				height: 64px;
			}
			.password-box {
				flex: 1;
				display: flex;
				width: 100%;
				position: relative;
				align-items: center;
				input {
					border: 1px solid #DADFE6;
					padding: 0 10px;
					color: #333;
					font-size: 16px;
					border-color: #acacac;
					border-radius: 0px;
					box-shadow:  inset 0px 3px 6px 1px rgba(0,0,0,0.16);
					background: #fff;
					flex: 1;
					width: 100%;
					border-width: 0 0 2px;
					border-style: solid;
					height: 64px;
				}
				input:focus {
					border: 1px solid #DADFE6;
					padding: 0 10px;
					color: #333;
					font-size: 16px;
					border-color: #acacac;
					border-radius: 0px;
					box-shadow:  inset 0px 3px 6px 1px rgba(0,0,0,0.16);
					background: #fff;
					flex: 1;
					width: 100%;
					border-width: 0 0 2px;
					border-style: solid;
					height: 64px;
				}
				.iconfont {
					cursor: pointer;
					z-index: 1;
					color: #000;
					top: 0;
					font-size: 16px;
					line-height: 44px;
					position: absolute;
					right: 5px;
				}
			}
			input::placeholder {
				color: #C3C7CC;
				font-size: 16px;
			}
			::v-deep .el-select {
				flex: 1;
				width: 100%;
			}
			::v-deep .el-select .el-input__inner {
				border: 1px solid #DADFE6;
				padding: 0 10px;
				color: #333;
				font-size: 16px;
				border-color: #acacac;
				border-radius: 0px;
				box-shadow:  inset 0px 3px 6px 1px rgba(0,0,0,0.16);
				background: #fff;
				flex: 1;
				width: 100%;
				border-width: 0 0 2px;
				border-style: solid;
				height: 64px;
			}
			::v-deep .el-select .is-focus .el-input__inner {
				border: 1px solid #DADFE6;
				padding: 0 10px;
				color: #333;
				font-size: 16px;
				border-color: #acacac;
				border-radius: 0px;
				box-shadow:  inset 0px 3px 6px 1px rgba(0,0,0,0.16);
				background: #fff;
				flex: 1;
				width: 100%;
				border-width: 0 0 2px;
				border-style: solid;
				height: 64px;
			}
			::v-deep .el-select .el-input__inner::placeholder{
				color: #C3C7CC;
				font-size: 16px;
			}
		}
		.login-btn {
			margin: 20px auto 0;
			display: flex;
			width: 100%;
			justify-content: center;
			align-items: center;
			flex-wrap: wrap;
			.login-btn1 {
				width: 100%;
				order: 1;
			}
			.login-btn2 {
				flex: 2;
			}
			.login-btn3 {
				margin: 0;
				display: flex;
				justify-content: flex-end;
			}
			.loginInBt {
				border: 0;
				cursor: pointer;
				border-radius: 30px;
				padding: 0 24px;
				margin: 0 0 15px;
				outline: none;
				color: #FF7700;
				background: #FFEAD8;
				font-weight: bold;
				width: 100%;
				font-size: 16px;
				height: 47px;
			}
			.loginInBt:hover {
				opacity: 0.8;
			}
			.register {
				border: 0px solid #333;
				cursor: pointer;
				padding: 0 10px;
				margin: 0 10px 15px 0;
				color: #fff;
				font-weight: bold;
				text-decoration: underline;
				font-size: 16px;
				border-radius: 0px;
				outline: none;
				background: none;
				width: auto;
				height: 36px;
			}
			.register:hover {
				opacity: 0.8;
			}
			.forget {
				border: 0px solid #333;
				cursor: pointer;
				border-radius: 0px;
				padding: 0 10px;
				margin: 0 10px 15px 0;
				outline: none;
				color: #fff;
				background: none;
				width: auto;
				font-size: 16px;
				height: 36px;
			}
			.forget:hover {
				opacity: 0.8;
			}
		}
	}
}

</style>

