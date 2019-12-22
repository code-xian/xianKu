export default {
  namespaced: true,
  state: {
    userId: '',
    adminName: '',
    adminUsername: '',
    adminAuthority:''
  },
  mutations: {
    userId (state, userId) {
      state.userId = userId
    },
    adminName (state, adminName) {
      state.adminName = adminName
    },
    adminUsername (state, adminUsername) {
      state.adminUsername = adminUsername
    },
    adminAuthority(state, adminAuthority) {
      state.adminAuthority = adminAuthority
    }
  }
}
