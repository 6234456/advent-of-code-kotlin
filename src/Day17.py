def speed(x,y):
    if x > 0:
        x = x - 1
    elif x < 0:
        x = x + 1

    y = y - 1
    return (x,y)

def inArea(x,y):
    return x >= 282 and x <= 314 and y >= -80 and y <= -45

def exitArea(x,y):
    return x > 314 or y < -80

def trace(x,y):
    px, py = 0, 0
    res = []
    while True:
        if exitArea(px, py):
            return []

        if inArea(px, py):
            return res

        px, py = px + x, py + y
        res.append(py)
        x, y = speed(x, y)