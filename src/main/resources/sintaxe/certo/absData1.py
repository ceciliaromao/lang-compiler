class Ponto:
    def __init__(self):
        self._x = 0
        self._y = 0

def deslocX(d, p):
    p._x = (p._x + d)

def deslocY(d, p):
    p._y = (p._y + d)

def criaPonto(x, y):
    p = Ponto()
    p._x = x
    p._y = y
    return (p,)

def main():
    p = criaPonto(0, 0)[0]

if __name__ == "__main__":
    main()
