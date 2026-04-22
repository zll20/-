const utils = getApp().utils
const menu = getApp().menu
const api = getApp().api
const base = getApp().base
const validate = getApp().validate
const des = getApp().des
Page({
	data: {
		// 当前题目
		item: {},
		// 试卷名称
		paper: {},
		// 是否提交
		isSubmit: false,
		// 填空题答案
		answer: '',
		// 当前题目
		currentItem: 1,
		// 总分
		score: 0,
		// 倒计时定时器
		inter: null,
		// 题目列表
		list: [],
		// 用户
		user: {},
		// 倒计时
		count: 0,
		SecondToDate: '',
		padTop: 0,
		examno: '',
		// 是否存在主观题
		hasSuject: false,
	},
	async onLoad(options) {
		let rs2 = wx.getWindowInfo()
		this.data.examno = this.getUUID()
		let that = this
		this.data.score = 0;
		let table = wx.getStorageSync("nowTable");
		await api.get(`${table}/session`).then(rs=>{
			this.data.user = rs.data
		})
		// 获取试卷名称
		await api.get(`exampaper/info/${options.id}`).then(rs=>{
			this.data.paper = rs.data
		})
		// 获取试题名称
		await api.get('examquestion/list',{
			page: 1,
			limit: 999,
			paperid: options.id
		}).then(rs=>{
			for(let x in rs.data.list){
				rs.data.list[x].questionname = rs.data.list[x].questionname.replace(/img src/gi,"img style=\"width:100%;\" src");
			}
			rs.data.list.sort(function (a, b) {
				return (b.sequence - a.sequence)
			});
			for(let x in rs.data.list){
				if(rs.data.list[x].type==4){
					this.data.hasSuject = true
				}
			}
			for (let i = 0; i < rs.data.list.length; i++) {
				let options = JSON.parse(rs.data.list[i].options);
				for (let j = 0; j < options.length; j++) {
					options[j].checked = false;
				}
				rs.data.list[i].options = options;
			}
			this.setData({
				list: rs.data.list,
				padTop: rs2.statusBarHeight,
				item: rs.data.list[0]
			})
		})
		// 开启定时 
		this.data.count = this.data.paper.time*60;
		this.setData({
			SecondToDate: this.getText()
		})
		if (this.data.count > 0) {
			this.data.inter = setInterval(function() {
				that.data.count = that.data.count - 1;
				that.setData({
					SecondToDate: that.getText()
				})
				if (that.data.count < 0) {
					that.setData({
						SecondToDate: ''
					})
					clearInterval(that.data.inter);
					wx.showModal({
						title: '答题结束',
						content: '本次考试成绩：' + that.data.score,
						showCancel: false,
						success: async function(res) {
							if (res.confirm) {
								wx.navigateBack({
									delta: 1
								})
							}
						}
					});
				}
			}, 1000);
		}
	},
	destroyed: function() {
		clearInterval(this.data.inter);
	},
	getText() {
		var time = this.data.count;
		if (null != time && "" != time) {
			if (time > 60 && time < 60 * 60) {
				time =
					parseInt(time / 60.0) +
					"分钟" +
					parseInt((parseFloat(time / 60.0) - parseInt(time / 60.0)) * 60) +
					"秒";
			} else if (time >= 60 * 60 && time < 60 * 60 * 24) {
				time =
					parseInt(time / 3600.0) +
					"小时" +
					parseInt(
						(parseFloat(time / 3600.0) - parseInt(time / 3600.0)) * 60
					) +
					"分钟" +
					parseInt(
						(parseFloat(
								(parseFloat(time / 3600.0) - parseInt(time / 3600.0)) * 60
							) -
							parseInt(
								(parseFloat(time / 3600.0) - parseInt(time / 3600.0)) * 60
							)) *
						60
					) +
					"秒";
			} else if (time >= 60 * 60 * 24) {
				time =
					parseInt(time / 3600.0 / 24) +
					"天" +
					parseInt(
						(parseFloat(time / 3600.0 / 24) - parseInt(time / 3600.0 / 24)) *
						24
					) +
					"小时" +
					parseInt(
						(parseFloat(time / 3600.0) - parseInt(time / 3600.0)) * 60
					) +
					"分钟" +
					parseInt(
						(parseFloat(
								(parseFloat(time / 3600.0) - parseInt(time / 3600.0)) * 60
							) -
							parseInt(
								(parseFloat(time / 3600.0) - parseInt(time / 3600.0)) * 60
							)) *
						60
					) +
					"秒";
			} else {
				time = parseInt(time) + "秒";
			}
		}
		return time;
	},
	getUUID () {
		return new Date().getTime();
	},
	leaveTap() {
		let that = this;
		wx.showModal({
			title: '提示',
			content: '确定离开考试?未答题目按0分计算',
			success: async function(res) {
				if (res.confirm) {
					utils.msgBack('本次考试成绩：' + that.data.score);
				}
			}
		});
	},
	answerKeyInput(e){
		this.setData({
			answer: e.detail.value
		})
	},
	itemCheckTap(e) {
		let checked = e.currentTarget.dataset.checked
		let index = e.currentTarget.dataset.index
		if (this.data.item.type == 0 || this.data.item.type == 2) {
			for (let i = 0; i < this.data.item.options.length; i++) {
				this.data.item.options[i].checked = false;
			}
		}
		this.data.item.options[index].checked = !checked;
		if (this.data.item.type == 1) {
			let answer = []
			for (let i = 0; i < this.data.item.options.length; i++) {
				if (this.data.item.options[i].checked) {
					answer.push(this.data.item.options[i].code)
				}
			}
			this.data.answer = answer.sort().join(",");
		}
		if (this.data.item.type != 1) {
			this.data.answer = this.data.item.options[index].code;
		}
		this.setData({
			item: this.data.item,
			answer: this.data.answer
		})
	},
	async submitTap() {
		if (!this.data.answer) {
			utils.msg("请作答");
			return
		}
		let myscore = 0;
		if (this.data.answer == this.data.item.answer) {
			this.data.score =  Number(this.data.score) + Number(this.data.item.score);
			myscore = this.data.item.score;
		}
		for(let j in this.data.item.options){
			delete this.data.item.options[j].checked
		}
		// 保存答题记录
		let record = {
			userid: this.data.user.id,
			username: wx.getStorageSync("appExamName")?wx.getStorageSync("appExamName"):'',
			paperid: this.data.paper.id,
			papername: this.data.paper.name,
			questionid: this.data.item.id,
			questionname: this.data.item.questionname,
			options: JSON.stringify(this.data.item.options),
			score: this.data.item.score,
			answer: this.data.item.answer,
			analysis: this.data.item.analysis,
			myscore: myscore,
			myanswer: this.data.answer,
			ismark: this.data.hasSuject? 0 : 1,
			type: this.data.item.type,
			examno: this.data.examno,
		};
		api.post('examrecord/add',record).then(rs=>{
			var that = this;
			this.setData({
				isSubmit: true
			})
			if (this.data.currentItem == this.data.list.length) {
				wx.showModal({
					title: '答题结束',
					content: '本次考试成绩：' + that.data.score,
					showCancel: false,
					success: async function(res) {
						if (res.confirm) {
							wx.navigateBack({
								delta: 1
							})
						}
					}
				});
			}
		})
	},
	nextTap() {
		this.setData({
			isSubmit: false,
			currentItem: this.data.currentItem + 1,
			item: this.data.list[this.data.currentItem],
			answer: ''
		})
	},
})