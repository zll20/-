import Vue from 'vue';
import VueRouter from 'vue-router'
Vue.use(VueRouter);
import Index from '@/views/index'
import Home from '@/views/home'
import Login from '@/views/login'
import NotFound from '@/views/404'
import UpdatePassword from '@/views/update-password'
import pay from '@/views/pay'
import register from '@/views/register'
import center from '@/views/center'
import adminexam from '@/views/modules/exampaperlist/exam'
import exampaperlist from '@/views/modules/exampaperlist/list'
import xuesheng from '@/views/modules/xuesheng/list'
import dancifenlei from '@/views/modules/dancifenlei/list'
import danciku from '@/views/modules/danciku/list'
import dancixuexi from '@/views/modules/dancixuexi/list'
import xuexijihua from '@/views/modules/xuexijihua/list'
import forum from '@/views/modules/forum/list'
import exampaper from '@/views/modules/exampaper/list'
import examquestion from '@/views/modules/examquestion/list'
import examquestionbank from '@/views/modules/examquestionbank/list'
import examrecord from '@/views/modules/examrecord/list'
import popupremind from '@/views/modules/popupremind/list'
import smsregistercode from '@/views/modules/smsregistercode/list'
import users from '@/views/modules/users/list'
import discussdanciku from '@/views/modules/discussdanciku/list'
import examfailrecord from '@/views/modules/examfailrecord/list'
import config from '@/views/modules/config/list'
import tingli from '@/views/modules/tingli/list'
import wenkan from '@/views/modules/wenkan/list'

//2.配置路由   注意：名字
export const routes = [{
	path: '/',
	name: '系统首页',
	component: Index,
	children: [{
		path: '/',
		name: '系统首页',
		component: Home,
		meta: {icon:'', title:'center', affix: true}
	}, {
		path: '/updatePassword',
		name: '修改密码',
		component: UpdatePassword,
		meta: {icon:'', title:'updatePassword'}
	}, {
		path: '/pay',
		name: '支付',
		component: pay,
		meta: {icon:'', title:'pay'}
	}, {
		path: '/center',
		name: '个人信息',
		component: center,
		meta: {icon:'', title:'center'}
	}, {
		path: '/xuesheng',
		name: '学生',
		component: xuesheng
	}, {
		path: '/dancifenlei',
		name: '单词分类',
		component: dancifenlei
	}, {
		path: '/danciku',
		name: '单词库',
		component: danciku
	}, {
		path: '/dancixuexi',
		name: '单词学习',
		component: dancixuexi
	}, {
		path: '/xuexijihua',
		name: '学习计划',
		component: xuexijihua
	}, {
		path: '/forum',
		name: '互动学习',
		component: forum
	}, {
		path: '/exampaper',
		name: '英语测试',
		component: exampaper
	}, {
		path: '/examquestion',
		name: '试题',
		component: examquestion
	}, {
		path: '/examquestionbank',
		name: '试题库',
		component: examquestionbank
	}, {
		path: '/examrecord',
		name: '考试记录',
		component: examrecord
	}, {
		path: '/popupremind',
		name: '弹窗提醒',
		component: popupremind
	}, {
		path: '/smsregistercode',
		name: '短信验证码',
		component: smsregistercode
	}, {
		path: '/users',
		name: '管理员',
		component: users
	}, {
		path: '/discussdanciku',
		name: '单词库评论',
		component: discussdanciku
	}, {
		path: '/config/:type',
		name: '配置管理',
		component: config
	}, {
		path: '/examfailrecord',
		name: '错题本',
		component: examfailrecord
	}, {
		path: '/exampaperlist',
		name: '试卷列表',
		component: exampaperlist
	}, {
		path: '/tingli',
		name: '听力',
		component: tingli
	}, {
		path: '/wenkan',
		name: '文刊',
		component: wenkan
	}]
	},
	{
		path: '/adminexam',
		name: 'adminexam',
		component: adminexam,
		meta: {icon:'', title:'adminexam'}
	},
	{
		path: '/login',
		name: 'login',
		component: Login,
		meta: {icon:'', title:'login'}
	},
	{
		path: '/register',
		name: 'register',
		component: register,
		meta: {icon:'', title:'register'}
	},
	{
		path: '*',
		component: NotFound
	}
]

//3.实例化VueRouter  注意：名字
const router = new VueRouter({
	mode: 'hash',
	routes // （缩写）相当于 routes: routes
})
const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location) {
	return originalPush.call(this, location).catch(err => err)
}
export default router;
