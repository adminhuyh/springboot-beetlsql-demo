findByName
===
* 根据用户名查找
select id,name,age,email from user where true
    @if(!isEmpty(name)){
    	and 
    	name = #name#
    @}