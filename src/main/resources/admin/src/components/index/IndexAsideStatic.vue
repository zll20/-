<template>
	<div class="menu-preview">
		<!-- 竖向-2 -->
		<el-scrollbar :wrap-class="isCollapse ? 'scrollbar-wrapper scrollbar-wrapper-close' : 'scrollbar-wrapper scrollbar-wrapper-open'">
			<el-button :style="verticalStyle2[isCollapse?'close':'open'].btn.default" type="primary" @click="collapse">
				<span class="icon iconfont"
					:style="verticalStyle2[isCollapse?'close':'open'].btn.icon.default"
					:class="verticalStyle2[isCollapse?'close':'open'].btn.icon.text"></span>{{verticalStyle2[isCollapse?'close':'open'].btn.text}}
			</el-button>
			<div class="title" :style="verticalStyle2[isCollapse?'close':'open'].title.box.default">
				<el-image v-if="verticalStyle2[isCollapse?'close':'open'].title.img.flag" :style="verticalStyle2[isCollapse?'close':'open'].title.img.default" :src="topLogoUrl?($base.url + topLogoUrl):verticalStyle2[isCollapse?'close':'open'].title.img.url" fit="cover"></el-image>
				<span class="title-name" :style="verticalStyle2[isCollapse?'close':'open'].title.text.default">{{verticalStyle2[isCollapse?'close':'open'].title.text.showType?verticalStyle2[isCollapse?'close':'open'].title.text.text:''}} {{this.$project.projectName}}</span>
			</div>
			<div class="userinfo"
				:style="verticalStyle2[isCollapse?'close':'open'].userinfo.box.default">
				<el-image v-if="avatar" :style="verticalStyle2[isCollapse?'close':'open'].userinfo.img.default" :src="avatar?this.$base.url + avatar:require('@/assets/img/avator.png')" fit="cover"></el-image>
				<div :style="verticalStyle2[isCollapse?'close':'open'].userinfo.nickname.default">
				{{this.$storage.get('adminName')}}</div>
			</div>
			<el-menu :default-active="activeMenu" :unique-opened="true" :style="verticalStyle2[isCollapse?'close':'open'].menu.box.default"
				class="el-menu-vertical-2" :collapse-transition="false" :collapse="isCollapse">
				<el-menu-item class="home" :popper-append-to-body="false" popper-class="home" @click.native="menuHandler('')" :style="verticalStyle2[isCollapse?'close':'open'].home.one.box.default" index="/">
					<div class="el-tooltip">
						<i :style="verticalStyle2[isCollapse?'close':'open'].home.one.icon.default"
							class="icon iconfont icon-shouye-zhihui"></i>
							<span :style="verticalStyle2[isCollapse?'close':'open'].home.one.title.default"
							slot="title">{{verticalStyle2.open.home.one.title.text}}</span>
					</div>
				</el-menu-item>
				<el-submenu class="user" popper-class="user" :popper-append-to-body="false"
					:style="verticalStyle2[isCollapse?'close':'open'].user.one.box.default" index="1">
					<template slot="title">
						<i :style="verticalStyle2[isCollapse?'close':'open'].user.one.icon.default"
							class="icon iconfont icon-kuaijiezhifu"></i>
						<span :style="verticalStyle2[isCollapse?'close':'open'].user.one.title.default"
							slot="title">{{verticalStyle2.open.user.one.title.text}}</span>
					</template>
					<el-menu-item index="/updatePassword" @click="menuHandler('updatePassword')">修改密码</el-menu-item>
					<el-menu-item index="/center" @click="menuHandler('center')">个人信息</el-menu-item>
				</el-submenu>
				<template v-for="(menu,index) in menuList.backMenu" v-if="menu.child.length&&menu.child[0].tableName!='hasBoard'&&menu.child[0].tableName!='storeup'&&(menu.child[0].tableName.length<=7||(menu.child[0].tableName.length>7&&menu.child[0].tableName.substring(0,7)!='chapter'))">
					<el-submenu v-if="menu.child.length > 1 || !verticalIsMultiple" class="other" popper-class="other" :popper-append-to-body="false" :style="verticalStyle2[isCollapse?'close':'open'].menu.one.box.default" :index="index+2+''">
						<template slot="title">
							<i :style="verticalStyle2[isCollapse?'close':'open'].menu.one.icon.default" class="el-icon-menu" :class="icons[index]"></i>
							<span :style="verticalStyle2[isCollapse?'close':'open'].menu.one.title.default" slot="title">{{nameChange(menu.menu, verticalFlag)}}</span>
						</template>
						<el-menu-item v-for="(child,sort) in menu.child" :key="sort" :index="'/'+child.tableName" @click="menuHandler(child.tableName)">{{ child.menu }}</el-menu-item>
					</el-submenu>
					<el-menu-item v-if="menu.child.length <= 1 && verticalIsMultiple" class="other" popper-class="other" :style="verticalStyle2[isCollapse?'close':'open'].menu.one.box.default" @click="menuHandler(menu.child[0].tableName)" :index="'/'+menu.child[0].tableName">
						<div class="el-tooltip">
							<i :style="verticalStyle2[isCollapse?'close':'open'].menu.one.icon.default" class="el-icon-menu" :class="icons[index]"></i>
							<span :style="verticalStyle2[isCollapse?'close':'open'].menu.one.title.default" slot="title">{{nameChange(menu.child[0].menu, verticalFlag)}}</span>
						</div>
					</el-menu-item>
				</template>
			</el-menu>
		</el-scrollbar>


	</div>
</template>

<script>
import menu from '@/utils/menu'
export default {
	data() {
		return {
			menuList: [],
			dynamicMenuRoutes: [],
			role: '',
			user: null,
			avatar:'',
			icons: [
				'el-icon-s-cooperation',
				'el-icon-s-order',
				'el-icon-s-platform',
				'el-icon-s-fold',
				'el-icon-s-unfold',
				'el-icon-s-operation',
				'el-icon-s-promotion',
				'el-icon-s-release',
				'el-icon-s-ticket',
				'el-icon-s-management',
				'el-icon-s-open',
				'el-icon-s-shop',
				'el-icon-s-marketing',
				'el-icon-s-flag',
				'el-icon-s-comment',
				'el-icon-s-finance',
				'el-icon-s-claim',
				'el-icon-s-custom',
				'el-icon-s-opportunity',
				'el-icon-s-data',
				'el-icon-s-check',
				'el-icon-s-grid',
				'el-icon-menu',
				'el-icon-chat-dot-square',
				'el-icon-message',
				'el-icon-postcard',
				'el-icon-position',
				'el-icon-microphone',
				'el-icon-close-notification',
				'el-icon-bangzhu',
				'el-icon-time',
				'el-icon-odometer',
				'el-icon-crop',
				'el-icon-aim',
				'el-icon-switch-button',
				'el-icon-full-screen',
				'el-icon-copy-document',
				'el-icon-mic',
				'el-icon-stopwatch',
			],
			menulistBorderBottom: {},
			topLogoUrl: '',
			verticalFlag: false,
			isCollapse: false,
			verticalStyle2: {"isCollapse":false,"close":{"contentBox":{"hover":{},"active":{"margin":"0 0 0 54px"},"default":{"minHeight":"100%","padding":"0 0 0 140px","margin":"0","position":"relative","background":"#fafafe","display":"block"}},"box":{"hover":{},"active":{"width":"54px"},"default":{"boxShadow":"1px 0 0px  #e7e8fc","overflow":"hidden","top":"81px","left":"0","background":"#fff","bottom":"0","width":"140px","position":"fixed","transition":"width 0.3s","height":"100%","zIndex":"1001"}},"title":{"img":{"hover":{},"default":{"width":"44px","objectFit":"cover","borderRadius":"100%","height":"44px"},"flag":true,"url":"http://codegen.caihongy.cn/20201114/7856ba26477849ea828f481fa2773a95.jpg"},"box":{"hover":{},"default":{"width":"100%","padding":"20px","display":"none","height":"auto"}},"text":{"hover":{},"showType":true,"default":{"padding":"0 0 0 12px","lineHeight":"44px","fontSize":"12px","color":"rgba(64, 158, 255, 1)"},"text":"欢迎使用"}},"menu":{"two":{"title":{"hover":{"padding":"0 20px","backgroundColor":"#ffbf00 !important","lineHeight":"44px","color":"#fff","height":"44px"},"active":{"padding":"0 20px","backgroundColor":"#ffbf00 !important","lineHeight":"44px","color":"#fff","height":"44px"},"default":{"padding":"0 20px","backgroundColor":"#fff","lineHeight":"44px","color":"#666","height":"44px"}},"box":{"hover":{},"default":{"border":"none"}}},"box":{"hover":{},"default":{"border":0,"padding":"20px 0 0","listStyle":"none","margin":"0","background":"none","width":"100%","position":"relative"}},"one":{"box1":{"hover":{"color":"#ff7a00","background":"#ffeede"},"active":{"color":"#ff7a00","background":"#ffeede","height":"auto !important"},"default":{"cursor":"pointer","minHeight":"82px","padding":"0 20px","color":"#333","background":"none","width":"100%","position":"relative","height":"auto"}},"icon":{"hover":{},"default":{"verticalAlign":"middle","margin":"0","color":"#ff7f00","textAlign":"center","background":"none","display":"block","width":"100%","fontSize":"42px","height":"42px"},"flag":true},"box":{"hover":{},"default":{"padding":"0","listStyle":"none","margin":"0","height":"auto"}},"title":{"hover":{},"default":{"verticalAlign":"middle","color":"#333","visibility":"visible","textAlign":"center","display":"block","width":"100%","fontSize":"16px","height":"50px"}},"arrow":{"hover":{},"default":{"verticalAlign":"middle","margin":"-7px 0 0 0","top":"50%","color":"inherit","display":"none","fontSize":"12px","position":"absolute","right":"20px"}}}},"btn":{"icon":{"hover":{},"default":{"margin":"0 2px","fontSize":"26px","color":"#ff7a00","height":"40px"},"text":"icon-menu02"},"hover":{"opacity":"0.8"},"default":{"border":"0","cursor":"pointer","padding":"0 9px","margin":"10px 0 10px 10px","outline":"none","color":"#ff7a00","borderRadius":"0","background":"rgba(64, 158, 255, 0)","display":"none","width":"auto","fontSize":"14px","height":"40px"},"text":""},"user":{"two":{"title":{"hover":{"padding":"0 20px","backgroundColor":"#ffbf00 !important","lineHeight":"44px","color":"#fff","height":"44px"},"active":{"padding":"0 20px","backgroundColor":"#ffbf00 !important","lineHeight":"44px","color":"#fff","height":"44px"},"default":{"padding":"0 20px","backgroundColor":"#fff","lineHeight":"44px","color":"#656","height":"44px"}},"box":{"hover":{},"default":{"border":"none"}}},"one":{"box1":{"hover":{"color":"#ff7a00","background":"#ffeede"},"active":{"color":"#ff7a00","background":"#ffeede"},"default":{"cursor":"pointer","minHeight":"82px","padding":"0 20px","color":"#333","background":"none","width":"100%","position":"relative","height":"auto"}},"icon":{"hover":{},"default":{"verticalAlign":"middle","margin":"0","color":"#ff7f00","textAlign":"center","background":"none","display":"block","width":"100%","fontSize":"42px","height":"42px"},"flag":true,"text":"icon-kuaijiezhifu"},"box":{"hover":{},"default":{"padding":"0","listStyle":"none","margin":"0","height":"auto"}},"title":{"hover":{},"default":{"verticalAlign":"middle","color":"#333","visibility":"visible","textAlign":"center","display":"block","width":"100%","fontSize":"16px","height":"50px"}},"arrow":{"hover":{},"default":{"verticalAlign":"middle","margin":"-7px 0 0 0","top":"50%","color":"inherit","display":"none","fontSize":"12px","position":"absolute","right":"20px"}}}},"userinfo":{"nickname":{"hover":{},"default":{"fontSize":"24px","lineHeight":"1.5","color":"#fff","textAlign":"center"}},"img":{"hover":{},"default":{"width":"100%","objectFit":"cover","borderRadius":"20px","display":"block","height":"170px"}},"box":{"hover":{},"default":{"width":"100%","padding":"20px","display":"none","height":"auto"}}},"home":{"two":{"title":{"hover":{"padding":"0 20px","backgroundColor":"red !important","lineHeight":"56px","color":"#fff","height":"56px"},"active":{"padding":"0 20px","backgroundColor":"blue !important","lineHeight":"56px","color":"#fff","height":"56px"},"default":{"padding":"0 20px","backgroundColor":"#fff","lineHeight":"56px","color":"#646","height":"56px"}},"box":{"hover":{},"default":{"border":"none"}}},"one":{"box1":{"hover":{"color":"#ff7a00","background":"#ffeede"},"active":{"color":"#ff7a00","background":"#ffeede"},"default":{"cursor":"pointer","minHeight":"82px","padding":"0 20px","color":"#333","background":"none","width":"100%","position":"relative","height":"auto"}},"icon":{"hover":{},"default":{"verticalAlign":"middle","margin":"0","color":"#ff7f00","textAlign":"center","background":"none","display":"block","width":"100%","fontSize":"42px","height":"42px"},"flag":true,"text":"icon-shouye-zhihui"},"box":{"hover":{},"default":{"padding":"0","listStyle":"none","margin":"0","height":"auto"}},"title":{"hover":{},"default":{"verticalAlign":"middle","color":"#333","visibility":"visible","textAlign":"center","display":"block","width":"100%","fontSize":"16px","height":"50px"}},"arrow":{"hover":{},"default":{"verticalAlign":"middle","margin":"-7px 0 0 0","top":"50%","color":"inherit","display":"none","fontSize":"12px","position":"absolute","right":"20px"}}}}},"open":{"contentBox":{"hover":{},"default":{"minHeight":"100%","padding":"0","margin":"0 0 0 250px","position":"relative","background":"url(http://codegen.caihongy.cn/20250826/9c566af26d2e4baca85799a2893fb657.png) no-repeat center top / cover","display":"block"}},"box":{"hover":{},"default":{"boxShadow":"0px 0 0px  #ddd","padding":"80px 0 0 0","bottom":"0","transition":"width 0.3s","overflow":"hidden","top":"0","borderRadius":"0","left":"0","background":"url(http://codegen.caihongy.cn/20250827/a7f785018ead4e91a377828cb870bef5.jpg) no-repeat right top / cover","width":"250px","position":"fixed","height":"100%","zIndex":"1001"}},"title":{"img":{"hover":{},"default":{"width":"44px","objectFit":"cover","borderRadius":"100%","height":"44px"},"flag":false,"url":"http://codegen.caihongy.cn/20201114/7856ba26477849ea828f481fa2773a95.jpg"},"box":{"hover":{},"default":{"width":"100%","padding":"20px","display":"none","height":"auto"}},"text":{"hover":{},"showType":true,"default":{"padding":"0 0 0 12px","lineHeight":"44px","fontSize":"12px","color":"rgba(64, 158, 255, 1)"},"text":"欢迎使用"}},"menu":{"two":{"title":{"hover":{"color":"#fff","borderRadius":"0","background":"linear-gradient( 270deg, #FF7700 0%, #FFB300 100%)"},"active":{"color":"#652bfb","borderRadius":"0","background":"#fff"},"default":{"padding":"0 0 0 40px","color":"#FA6B04","borderRadius":"8px","background":"none","lineHeight":"51px","fontSize":"16px","minWidth":"100%","height":"51px"}},"box":{"hover":{},"default":{"border":"none","margin":"0px 10%","borderRadius":"8px","background":"#FFF9F6"}}},"box":{"hover":{},"default":{"border":0,"padding":"0px 0px 100px 0","listStyle":"none","margin":"0px  0 0  0","flexWrap":"wrap","background":"none","display":"flex","position":"relative"}},"one":{"box1":{"hover":{"margin":"15px 10%","color":"#fff","borderRadius":"0","background":"linear-gradient( 270deg, #FF7700 0%, #FFB300 100%)"},"active":{"margin":"15px 10%","color":"#fff","borderRadius":"0","background":"linear-gradient( 270deg, #FF7700 0%, #FFB300 100%)"},"default":{"cursor":"pointer","padding":"0 10px","margin":"15px 10%","whiteSpace":"nowrap","color":"#FA6B04","borderRadius":"0px","background":"none","lineHeight":"51px","position":"relative","fontWeight":"400","height":"51px"}},"icon":{"hover":{},"default":{"verticalAlign":"middle","margin":"0 3px","color":"inherit","borderRadius":"4px","textAlign":"center","display":"inline-block","width":"24px","fontSize":"18px","lineHeight":"24px","fontWeight":"bold","height":"24px"},"flag":true},"box":{"hover":{},"default":{"width":"100%","padding":"0","listStyle":"none","margin":"0"}},"title":{"hover":{},"default":{"color":"inherit","verticalAlign":"middle","fontSize":"16px"}},"arrow":{"hover":{},"default":{"verticalAlign":"middle","margin":"-7px 0 0 0","top":"50%","color":"inherit","fontSize":"12px","position":"absolute","right":"20px"}}}},"btn":{"icon":{"hover":{},"default":{"margin":"0 2px","fontSize":"26px","color":"#ff7a00","height":"40px"},"text":"icon-menu02"},"hover":{"opacity":"0.8"},"default":{"border":"0","cursor":"pointer","padding":"0 9px","outline":"none","margin":"10px 0 10px 10px","color":"#ff7a00","borderRadius":"4px","background":"rgba(64, 158, 255, 0)","display":"none","width":"auto","fontSize":"14px","height":"40px"},"text":""},"user":{"two":{"title":{"hover":{"color":"#fff","borderRadius":"0","background":"linear-gradient( 270deg, #FF7700 0%, #FFB300 100%)"},"active":{"color":"#fff","borderRadius":"0","background":"linear-gradient( 270deg, #FF7700 0%, #FFB300 100%)"},"default":{"padding":"0 0 0 40px","color":"#FA6B04","borderRadius":"8px","background":"none","lineHeight":"51px","fontSize":"16px","minWidth":"100%","height":"51px"}},"box":{"hover":{},"default":{"border":"none","margin":"0px 10%","borderRadius":"8px","background":"#FFF9F6"}}},"one":{"box1":{"hover":{"margin":"15px 10%","color":"#fff","borderRadius":"0","background":"linear-gradient( 270deg, #FF7700 0%, #FFB300 100%)"},"active":{"margin":"15px 10%","color":"#fff","borderRadius":"0","background":"linear-gradient( 270deg, #FF7700 0%, #FFB300 100%)"},"default":{"cursor":"pointer","padding":"0 10px","margin":"15px 10%","whiteSpace":"nowrap","color":"#FA6B04","borderRadius":"0px","background":"none","position":"relative","fontWeight":"400"}},"icon":{"hover":{},"default":{"verticalAlign":"middle","margin":"0 3px","color":"inherit","borderRadius":"4px","textAlign":"center","display":"inline-block","width":"24px","fontSize":"18px","lineHeight":"24px","fontWeight":"bold","height":"24px"},"flag":true,"text":"icon-kuaijiezhifu"},"box":{"hover":{},"default":{"width":"100%","padding":"0","listStyle":"none","margin":"0 0 20px","order":"22"}},"title":{"hover":{},"default":{"color":"inherit","verticalAlign":"middle","fontSize":"16px"},"text":"个人中心"},"arrow":{"hover":{},"default":{"verticalAlign":"middle","margin":"-7px 0 0 0","top":"50%","color":"inherit","fontSize":"12px","position":"absolute","right":"20px"}}}},"userinfo":{"nickname":{"hover":{},"default":{"fontSize":"18px","lineHeight":"2","color":"#333","textAlign":"left"}},"img":{"hover":{},"default":{"width":"54px","margin":"0 6px 0 0","objectFit":"cover","borderRadius":"100%","display":"block","height":"54px"}},"box":{"hover":{},"default":{"padding":"20px 0","borderColor":"#ddd","alignItems":"center","textAlign":"center","borderWidth":"0 0 1px","display":"none","width":"100%","borderStyle":"solid","justifyContent":"center","height":"auto"}}},"home":{"two":{"title":{"hover":{"padding":"0 40px","lineHeight":"50px","color":"#fff","background":"red","height":"50px"},"active":{"padding":"0 40px","lineHeight":"50px","color":"#fff","background":"blue","height":"50px"},"default":{"padding":"0 40px","lineHeight":"50px","color":"#664","background":"#fff","height":"50px"}},"box":{"hover":{},"default":{"border":"none","display":"none"}}},"one":{"box1":{"hover":{"margin":"15px 10%","color":"#fff","borderRadius":"0","background":"linear-gradient( 270deg, #FF7700 0%, #FFB300 100%)"},"active":{"margin":"15px 10%","color":"#fff","borderRadius":"0","background":"linear-gradient( 270deg, #FF7700 0%, #FFB300 100%)"},"default":{"cursor":"pointer","padding":"0 10px","margin":"15px 10%","whiteSpace":"nowrap","color":"#FA6B04","borderRadius":"0px","background":"none","position":"relative","fontWeight":"400"}},"icon":{"hover":{},"default":{"verticalAlign":"middle","margin":"0 3px","color":"inherit","borderRadius":"4px","textAlign":"center","display":"inline-block","width":"24px","fontSize":"18px","lineHeight":"24px","fontWeight":"bold","height":"24px"},"flag":true,"text":"icon-shouye-zhihui"},"box":{"hover":{},"default":{"width":"100%","padding":"0","listStyle":"none","margin":"0"}},"title":{"hover":{},"default":{"color":"inherit","verticalAlign":"middle","fontSize":"16px"},"text":"系统首页"},"arrow":{"hover":{},"default":{"verticalAlign":"middle","margin":"-7px 0 0 0","top":"50%","color":"inherit","fontSize":"12px","position":"absolute","right":"20px"}}}}}},
			verticalIsMultiple: true,
		}
	},
	computed: {
		activeMenu() {
			const route = this.$route
			const {
				meta,
				path
			} = route
			// if st path, the sidebar will highlight the path you sete
			if (meta.activeMenu) {
				return meta.activeMenu
			}
			return path
		}
	},
	watch:{
		avatar(){
			this.$forceUpdate()
		},
	},
	mounted() {
		let menus = menu.list()
			if(menus) {
				let arr = []
				let brr = []
				menus.forEach(item=>{
				if(item.roleName == this.$storage.get('role')) {
				}
			})
			
			this.menuList = menus
		} else {
			let params = {
				page: 1,
				limit: 1,
				sort: 'id',
			}
			
			this.$http({
				url: "menu/list",
				method: "get",
				params: params
			}).then(({
				data
			}) => {
				if (data && data.code === 0) {
					let arr = []
					let brr = []
					menus = JSON.parse(data.data.list[0].menujson)
					menus.forEach(item=>{
						if(item.roleName == this.$storage.get('role')) {
						}
					})
					this.menuList = menus;
					this.$storage.set("menus", this.menuList);
				}
			})
		}
		this.role = this.$storage.get('role')
		
		for(let i=0;i<this.menuList.length;i++) {
			if(this.menuList[i].roleName == this.role) {
				this.menuList = this.menuList[i];
				break;
			}
		}
		this.styleChange()
		
		let sessionTable = this.$storage.get("sessionTable")
		this.$http({
			url: sessionTable + '/session',
			method: "get"
		}).then(({
			data
		}) => {
			if (data && data.code === 0) {
				if(sessionTable == 'xuesheng') {
					this.avatar = data.data.touxiang
				}
				if(sessionTable=='users') {
					this.avatar = data.data.image
				}
				this.user = data.data;
			} else {
				let message = this.$message
				message.error(data.msg);
			}
		});
	},
	created(){
		this.icons.sort(()=>{
			return (0.5-Math.random())
		})
	},
	methods: {
		nameChange(e,type) {
			if(e=='订单管理') {
				return e
			}
			if(type) {
				return e + '管理'
			}
			return e
		},
		collapse() {
		  this.isCollapse = !this.isCollapse
		  this.$emit('oncollapsechange', this.isCollapse)
		},
		styleChange() {
			this.$nextTick(() => {
								document.querySelectorAll('.el-menu-vertical-demo .el-submenu .el-menu').forEach(el => {
				  el.removeAttribute('style')
				  const icon = {"border":"none","display":"none"}
				  Object.keys(icon).forEach((key) => {
					el.style[key] = icon[key]
				  })
				})
											})
		},
		menuHandler(name) {
			let router = this.$router
			name = '/'+name
			router.push(name)
		},
	}
}
</script>
<style lang="scss" scoped>
	.menu-preview {
		.el-scrollbar {
			height: 100%;
	
			& ::v-deep .scrollbar-wrapper {
				overflow-x: hidden;
			}
		
			// 竖向
			.el-menu-vertical-demo {
				.el-submenu:first-of-type ::v-deep .el-submenu__title .el-submenu__icon-arrow {
					display: none;
				}
			}
			
			.el-menu-vertical-demo>.el-menu-item {
				cursor: pointer;
				padding: 0 20px;
				color: #333;
				white-space: nowrap;
				background: #fff;
				position: relative;
			}
			
			.el-menu-vertical-demo>.el-menu-item:hover {
				color: #fff;
				background: blue;
			}
			
			.el-menu-vertical-demo .el-submenu ::v-deep .el-submenu__title {
				cursor: pointer;
				padding: 0 20px;
				color: #333;
				white-space: nowrap;
				background: #fff;
				position: relative;
			}
			
			.el-menu-vertical-demo .el-submenu ::v-deep .el-submenu__title:hover {
				color: #fff;
				background: blue;
			}
			
			.el-menu-vertical-demo .el-submenu ::v-deep .el-submenu__title .el-submenu__icon-arrow {
				margin: -7px 0 0 0;
				top: 50%;
				color: inherit;
				vertical-align: middle;
				font-size: 12px;
				position: absolute;
				right: 20px;
			}
			
			.el-menu-vertical-demo .el-submenu {
				padding: 0;
				margin: 0;
				list-style: none;
			}
			
			// .el-menu-vertical-demo .el-submenu ::v-deep .el-menu {
// 					// 		border: none;
// 					// 		display: none;
// 					// }
			
			.el-menu-vertical-demo .el-submenu ::v-deep .el-menu .el-menu-item {
				padding: 0 40px;
				color: #666;
				background: #fff;
				line-height: 50px;
				height: 50px;
			}
			
			.el-menu-vertical-demo .el-submenu ::v-deep .el-menu .el-menu-item:hover {
				padding: 0 40px;
				color: #fff;
				background: red;
				line-height: 50px;
				height: 50px;
			}
			
			.el-menu-vertical-demo .el-submenu ::v-deep .el-menu .el-menu-item.is-active {
				padding: 0 40px;
				color: #fff;
				background: blue;
				line-height: 50px;
				height: 50px;
			}
			// 竖向
		}
	}
	// 竖向 样式二-open
	.scrollbar-wrapper-open .el-menu-vertical-2>.el-menu-item.other {
		font-size: inherit;
		background: none;
	}
	.scrollbar-wrapper-open .el-menu-vertical-2>.el-menu-item.home {
		font-size: inherit;
		background: none;
	}
	.scrollbar-wrapper-open .el-menu-vertical-2>.el-menu-item.other>.el-tooltip {
		cursor: pointer;
		border-radius: 0px;
		padding: 0 10px;
		margin: 15px 10%;
		color: #FA6B04;
		white-space: nowrap;
		background: none;
		font-weight: 400;
		line-height: 51px;
		position: relative;
		height: 51px;
	}
	
	.scrollbar-wrapper-open .el-menu-vertical-2>.el-menu-item.other>.el-tooltip:hover {
		border-radius: 0 !important;
		margin: 15px 10% !important;
		color: #fff !important;
		background: linear-gradient( 270deg, #FF7700 0%, #FFB300 100%) !important;
	}
	
	
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.other ::v-deep .el-submenu__title {
		cursor: pointer !important;
		border-radius: 0px !important;
		padding: 0 10px !important;
		margin: 15px 10% !important;
		color: #FA6B04 !important;
		white-space: nowrap !important;
		background: none !important;
		font-weight: 400 !important;
		line-height: 51px !important;
		position: relative !important;
		height: 51px !important;
	}
	.scrollbar-wrapper-open .el-menu-vertical-2>.el-menu-item.other.is-active>.el-tooltip {
		border-radius: 0 !important;
		margin: 15px 10% !important;
		color: #fff !important;
		background: linear-gradient( 270deg, #FF7700 0%, #FFB300 100%) !important;
	}
	
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.other ::v-deep .el-submenu__title:hover {
		border-radius: 0 !important;
		margin: 15px 10% !important;
		color: #fff !important;
		background: linear-gradient( 270deg, #FF7700 0%, #FFB300 100%) !important;
	}
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.other.is-active ::v-deep .el-submenu__title {
		border-radius: 0 !important;
		margin: 15px 10% !important;
		color: #fff !important;
		background: linear-gradient( 270deg, #FF7700 0%, #FFB300 100%) !important;
	}
	
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.other ::v-deep .el-submenu__title .iconfont {
		border-radius: 4px;
		margin: 0 3px;
		color: inherit;
		font-weight: bold;
		display: inline-block;
		vertical-align: middle;
		width: 24px;
		font-size: 18px;
		line-height: 24px;
		text-align: center;
		height: 24px;
	}
	
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.other ::v-deep .el-submenu__title .el-submenu__icon-arrow {
		margin: -7px 0 0 0;
		top: 50%;
		color: inherit;
		vertical-align: middle;
		font-size: 12px;
		position: absolute;
		right: 20px;
	}
	
	.scrollbar-wrapper-open .el-menu-vertical-2 ::v-deep .el-submenu.other .el-menu {
		border: none;
		border-radius: 8px;
		margin: 0px 10%;
		background: #FFF9F6;
	}
	
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.other .el-menu .el-menu-item {
		border-radius: 8px !important;
		padding: 0 0 0 40px !important;
		color: #FA6B04 !important;
		background: none !important;
		font-size: 16px !important;
		line-height: 51px !important;
		min-width: 100% !important;
		height: 51px !important;
	}
	
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.other .el-menu .el-menu-item:hover {
		border-radius: 0 !important;
		color: #fff !important;
		background: linear-gradient( 270deg, #FF7700 0%, #FFB300 100%) !important;
	}
	
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.other .el-menu .el-menu-item.is-active {
		border-radius: 0 !important;
		color: #652bfb !important;
		background: #fff !important;
	}

	// 竖向 样式二-close
	.scrollbar-wrapper-close .el-menu-vertical-2>.el-menu-item.other>.el-tooltip {
		cursor: pointer;
		padding: 0 20px;
		color: #333;
		background: none;
		width: 100%;
		min-height: 82px;
		position: relative;
		height: auto;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2>.el-menu-item.other>.el-tooltip:hover {
		color: #ff7a00;
		background: #ffeede;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2>.el-menu-item.other.is-active>.el-tooltip {
		color: #ff7a00;
		background: #ffeede;
		height: auto !important;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.other ::v-deep .el-submenu__title {
		cursor: pointer !important;
		padding: 0 20px !important;
		color: #333 !important;
		background: none !important;
		width: 100% !important;
		min-height: 82px !important;
		position: relative !important;
		height: auto !important;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.other ::v-deep .el-submenu__title:hover {
		color: #ff7a00 !important;
		background: #ffeede !important;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.other ::v-deep .el-submenu__title .iconfont {
		margin: 0;
		color: #ff7f00;
		background: none;
		display: block;
		vertical-align: middle;
		width: 100%;
		font-size: 42px;
		text-align: center;
		height: 42px;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.other ::v-deep .el-submenu__title .el-submenu__icon-arrow {
		margin: -7px 0 0 0;
		top: 50%;
		color: inherit;
		display: none;
		vertical-align: middle;
		font-size: 12px;
		position: absolute;
		right: 20px;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.other .el-menu {
		border: none;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.other .el-menu--vertical.other .el-menu-item {
		background-color: #fff;
		padding: 0 20px;
		color: #666;
		line-height: 44px;
		height: 44px;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.other .el-menu--vertical.other .el-menu-item:hover {
		background-color: #ffbf00 !important;
		padding: 0 20px;
		color: #fff;
		line-height: 44px;
		height: 44px;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.other .el-menu--vertical.other .el-menu-item.is-active {
		background-color: #ffbf00 !important;
		padding: 0 20px;
		color: #fff;
		line-height: 44px;
		height: 44px;
	}
	
	// 竖向 样式二-open-首页
	.scrollbar-wrapper-open .el-menu-vertical-2>.el-menu-item.home>.el-tooltip {
		cursor: pointer;
		border-radius: 0px;
		padding: 0 10px;
		margin: 15px 10%;
		color: #FA6B04;
		white-space: nowrap;
		background: none;
		font-weight: 400;
		line-height: 51px;
		position: relative;
		height: 51px;
	}
	
	.scrollbar-wrapper-open .el-menu-vertical-2>.el-menu-item.home>.el-tooltip:hover {
		border-radius: 0;
		margin: 15px 10%;
		color: #fff;
		background: linear-gradient( 270deg, #FF7700 0%, #FFB300 100%);
	}
	
	.scrollbar-wrapper-open .el-menu-vertical-2>.el-menu-item.home.is-active>.el-tooltip {
		border-radius: 0;
		margin: 15px 10%;
		color: #fff;
		background: linear-gradient( 270deg, #FF7700 0%, #FFB300 100%);
	}
	
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.home ::v-deep .el-submenu__title {
		cursor: pointer !important;
		border-radius: 0px !important;
		padding: 0 10px !important;
		margin: 15px 10% !important;
		color: #FA6B04 !important;
		white-space: nowrap !important;
		background: none !important;
		font-weight: 400 !important;
		line-height: 51px !important;
		position: relative !important;
		height: 51px !important;
	}
	
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.home ::v-deep .el-submenu__title:hover {
		border-radius: 0 !important;
		margin: 15px 10% !important;
		color: #fff !important;
		background: linear-gradient( 270deg, #FF7700 0%, #FFB300 100%) !important;
	}
	
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.home ::v-deep .el-submenu__title .iconfont {
		border-radius: 4px;
		margin: 0 3px;
		color: inherit;
		font-weight: bold;
		display: inline-block;
		vertical-align: middle;
		width: 24px;
		font-size: 18px;
		line-height: 24px;
		text-align: center;
		height: 24px;
	}
	
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.home ::v-deep .el-submenu__title .el-submenu__icon-arrow {
		margin: -7px 0 0 0;
		top: 50%;
		color: inherit;
		vertical-align: middle;
		font-size: 12px;
		position: absolute;
		right: 20px;
	}
	
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.home .el-menu {
		border: none;
		border-radius: 8px;
		margin: 0px 10%;
		background: #FFF9F6;
	}
	
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.home .el-menu .el-menu-item {
		border-radius: 8px;
		padding: 0 0 0 40px;
		color: #FA6B04;
		background: none;
		font-size: 16px;
		line-height: 51px;
		min-width: 100%;
		height: 51px;
	}
	
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.home .el-menu .el-menu-item:hover {
		border-radius: 0;
		color: #fff;
		background: linear-gradient( 270deg, #FF7700 0%, #FFB300 100%);
	}
	
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.home .el-menu .el-menu-item.is-active {
		border-radius: 0;
		color: #652bfb;
		background: #fff;
	}
	
	// 竖向 样式二-close-首页
	.scrollbar-wrapper-close .el-menu-vertical-2>.el-menu-item.home>.el-tooltip {
		cursor: pointer;
		padding: 0 20px;
		color: #333;
		background: none;
		width: 100%;
		min-height: 82px;
		position: relative;
		height: auto;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2>.el-menu-item.home>.el-tooltip:hover {
		color: #ff7a00;
		background: #ffeede;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2>.el-menu-item.home.is-active>.el-tooltip {
		color: #ff7a00;
		background: #ffeede;
		height: auto !important;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.home ::v-deep .el-submenu__title {
		cursor: pointer;
		padding: 0 20px;
		color: #333;
		background: none;
		width: 100%;
		min-height: 82px;
		position: relative;
		height: auto;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.home ::v-deep .el-submenu__title:hover {
		color: #ff7a00;
		background: #ffeede;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.home ::v-deep .el-submenu__title .iconfont {
		margin: 0;
		color: #ff7f00;
		background: none;
		display: block;
		vertical-align: middle;
		width: 100%;
		font-size: 42px;
		text-align: center;
		height: 42px;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.home ::v-deep .el-submenu__title .el-submenu__icon-arrow {
		margin: -7px 0 0 0;
		top: 50%;
		color: inherit;
		display: none;
		vertical-align: middle;
		font-size: 12px;
		position: absolute;
		right: 20px;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.home .el-menu {
		border: none;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.home .el-menu--vertical.home .el-menu-item {
		background-color: #fff;
		padding: 0 20px;
		color: #666;
		line-height: 44px;
		height: 44px;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.home .el-menu--vertical.home .el-menu-item:hover {
		background-color: #ffbf00 !important;
		padding: 0 20px;
		color: #fff;
		line-height: 44px;
		height: 44px;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.home .el-menu--vertical.home .el-menu-item.is-active {
		background-color: #ffbf00 !important;
		padding: 0 20px;
		color: #fff;
		line-height: 44px;
		height: 44px;
	}
	
	// 竖向 样式二-open-个人中心
	.scrollbar-wrapper-open .el-menu-vertical-2>.el-menu-item.user>.el-tooltip {
		cursor: pointer;
		border-radius: 0px;
		padding: 0 10px;
		margin: 15px 10%;
		color: #FA6B04;
		white-space: nowrap;
		background: none;
		font-weight: 400;
		line-height: 51px;
		position: relative;
		height: 51px;
	}
	
	.scrollbar-wrapper-open .el-menu-vertical-2>.el-menu-item.user>.el-tooltip:hover {
		border-radius: 0;
		margin: 15px 10%;
		color: #fff;
		background: linear-gradient( 270deg, #FF7700 0%, #FFB300 100%);
	}
	
	.scrollbar-wrapper-open .el-menu-vertical-2>.el-menu-item.user.is-active>.el-tooltip {
		border-radius: 0;
		margin: 15px 10%;
		color: #fff;
		background: linear-gradient( 270deg, #FF7700 0%, #FFB300 100%);
	}
	
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.user ::v-deep .el-submenu__title {
		cursor: pointer !important;
		border-radius: 0px !important;
		padding: 0 10px !important;
		margin: 15px 10% !important;
		color: #FA6B04 !important;
		white-space: nowrap !important;
		background: none !important;
		font-weight: 400 !important;
		line-height: 51px !important;
		position: relative !important;
		height: 51px !important;
	}
	
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.user ::v-deep .el-submenu__title:hover {
		border-radius: 0 !important;
		margin: 15px 10% !important;
		color: #fff !important;
		background: linear-gradient( 270deg, #FF7700 0%, #FFB300 100%) !important;
	}
	
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.user ::v-deep .el-submenu__title .iconfont {
		border-radius: 4px;
		margin: 0 3px;
		color: inherit;
		font-weight: bold;
		display: inline-block;
		vertical-align: middle;
		width: 24px;
		font-size: 18px;
		line-height: 24px;
		text-align: center;
		height: 24px;
	}
	
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.user ::v-deep .el-submenu__title .el-submenu__icon-arrow {
		margin: -7px 0 0 0;
		top: 50%;
		color: inherit;
		vertical-align: middle;
		font-size: 12px;
		position: absolute;
		right: 20px;
	}
	
	.scrollbar-wrapper-open .el-menu-vertical-2 ::v-deep .el-submenu.user .el-menu {
		border: none;
		border-radius: 8px;
		margin: 0px 10%;
		background: #FFF9F6;
	}
	
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.user .el-menu .el-menu-item {
		border-radius: 8px !important;
		padding: 0 0 0 40px !important;
		color: #FA6B04 !important;
		background: none !important;
		font-size: 16px !important;
		line-height: 51px !important;
		min-width: 100% !important;
		height: 51px !important;
	}
	
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.user .el-menu .el-menu-item:hover {
		border-radius: 0 !important;
		color: #fff !important;
		background: linear-gradient( 270deg, #FF7700 0%, #FFB300 100%) !important;
	}
	
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.user .el-menu .el-menu-item.is-active {
		border-radius: 0 !important;
		color: #652bfb !important;
		background: #fff !important;
	}
	
	// 竖向 样式二-close-个人中心
	.scrollbar-wrapper-close .el-menu-vertical-2>.el-menu-item.user>.el-tooltip {
		cursor: pointer;
		padding: 0 20px;
		color: #333;
		background: none;
		width: 100%;
		min-height: 82px;
		position: relative;
		height: auto;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2>.el-menu-item.user>.el-tooltip:hover {
		color: #ff7a00;
		background: #ffeede;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2>.el-menu-item.user.is-active>.el-tooltip {
		color: #ff7a00;
		background: #ffeede;
		height: auto !important;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.user ::v-deep .el-submenu__title {
		cursor: pointer !important;
		padding: 0 20px !important;
		color: #333 !important;
		background: none !important;
		width: 100% !important;
		min-height: 82px !important;
		position: relative !important;
		height: auto !important;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.user ::v-deep .el-submenu__title:hover {
		color: #ff7a00 !important;
		background: #ffeede !important;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.user ::v-deep .el-submenu__title .iconfont {
		margin: 0;
		color: #ff7f00;
		background: none;
		display: block;
		vertical-align: middle;
		width: 100%;
		font-size: 42px;
		text-align: center;
		height: 42px;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.user ::v-deep .el-submenu__title .el-submenu__icon-arrow {
		margin: -7px 0 0 0;
		top: 50%;
		color: inherit;
		display: none;
		vertical-align: middle;
		font-size: 12px;
		position: absolute;
		right: 20px;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.user .el-menu {
		border: none;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.user .el-menu--vertical.user .el-menu-item {
		background-color: #fff;
		padding: 0 20px;
		color: #666;
		line-height: 44px;
		height: 44px;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.user .el-menu--vertical.user .el-menu-item:hover {
		background-color: #ffbf00 !important;
		padding: 0 20px;
		color: #fff;
		line-height: 44px;
		height: 44px;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.user .el-menu--vertical.user .el-menu-item.is-active {
		background-color: #ffbf00 !important;
		padding: 0 20px;
		color: #fff;
		line-height: 44px;
		height: 44px;
	}
</style>

