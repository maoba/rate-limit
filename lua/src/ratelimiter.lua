---
--- Generated by EmmyLua(https://github.com/EmmyLua)
--- Created by ktdaddy.
--- DateTime: 2021/2/21 21:02
---模拟限流
---限流key
local key = "my key"

---限流阈值
local limit = 2

---当前流量大小
local currentLimit = 1

if currentLimit + 1 > limit then
    print('reject')
    return false
else
    print('accept')
    return true
end