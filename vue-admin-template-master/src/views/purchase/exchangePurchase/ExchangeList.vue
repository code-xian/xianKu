<template>
<div>
    采购退货列表{{data111}}
</div>
</template>

<script>
    export default {
        name: "ExchangeList",
        data(){
            return{
                data111:this.$store.state.user.userId
            }
        }
    }
</script>

<style scoped>

</style>
