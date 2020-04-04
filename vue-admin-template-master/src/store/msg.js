export default {
    namespaced: true,
    state: {
        message: [],
        rowCount: 0,
    },
    mutations: {
        pushMessage (state, message) {
            if(JSON.stringify(state.message).indexOf(JSON.stringify(message))==-1){
                state.message.push(message);
                state.rowCount++
            }
        },
        rowCount (state, rowCount) {
            // state.rowCount++
        },
        deleteMessage(state , message){
            state.message.map((value, index) => {
                if(value.text == message){
                    state.message.splice(index,1)
                }
            })
        }
    },
    actions:{
        push_message({commit},item) {
            commit('pushMessage',item)
        },
        delete_message({commit},item) {
            commit('deleteMessage',item)
        },
        add_rowCount({commit}) {
            commit('rowCount')
        }
    },
}
