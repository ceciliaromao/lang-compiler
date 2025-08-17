class Transition:
    def __init__(self):
        self.sym = ''
        self.dest = 0

class AFD:
    def __init__(self):
        self.st = None
        self.numt = None
        self.numStates = 0
        self.start = 0

def mkAutomata(numStates):
    m = AFD()
    m.st = [None] * (numStates)
    m.numt = [0] * (numStates)
    i = 0
    for _ in range(numStates):
        m.numt[i] = 1
        i = (i + 1)
    m.numStates = numStates
    m.start = 0
    return (m,)

def setFinal(m, st):
    m.numt[st] = (0 - m.numt[st])

def isFinal(m, st):
    return ((m.numt[st] < 0),)

def setNumTransitions(m, st, n):
    m.st[st] = [None] * (n)
    m.numt[st] = (n + 1)

def addTransition(m, st, a, d):
    i = 0
    add = True
    for _ in range((abs(m.numt[st])[0] - 1)):
        if ((m.st[st][i] == None) and add):
            m.st[st][i] = Transition()
            m.st[st][i].sym = a
            m.st[st][i].dest = d
            add = False
        i = (i + 1)

def abs(n):
    if (0 < n):
        return (n,)
    return ((0 - n),)

def printAutomata(m):
    st = 0
    for _ in range(m.numStates):
        if isFinal(m, st)[0]:
            print('*', end='')
        print(st, end='')
        print(' ', end='')
        print((abs(m.numt[st])[0] - 1), end='')
        print(' ', end='')
        if ((m.st[st] != None) and (0 < (abs(m.numt[st])[0] - 1))):
            k = 0
            for _ in range((abs(m.numt[st])[0] - 1)):
                if (m.st[st][k] != None):
                    print('[', end='')
                    print(m.st[st][k].sym, end='')
                    print(' ', end='')
                    print('-', end='')
                    print('>', end='')
                    print(' ', end='')
                    print(m.st[st][k].dest, end='')
                    print(']', end='')
                    print(' ', end='')
                    k = (k + 1)
        print()
        st = (st + 1)

def delta(m, st, c):
    if ((m.st[st] != None) and (0 < (abs(m.numt[st])[0] - 1))):
        k = 0
        for _ in range((abs(m.numt[st])[0] - 1)):
            if (m.st[st][k].sym == c):
                return (m.st[st][k].dest,)
            k = (k + 1)
    return ((0 - 1),)

def runAFDHelper(m, st, p, sz, s):
    if (p < sz):
        return (runAFDHelper(m, delta(m, st, s[p])[0], (p + 1), sz, s)[0],)
    return (st,)

def runAFD(m, s, sz):
    return (runAFDHelper(m, m.start, 0, sz, s)[0],)

def myAFD():
    afd = mkAutomata(3)[0]
    setNumTransitions(afd, 0, 2)
    addTransition(afd, 0, 'a', 0)
    addTransition(afd, 0, 'b', 1)
    setNumTransitions(afd, 1, 2)
    addTransition(afd, 1, 'a', 0)
    addTransition(afd, 1, 'b', 2)
    setNumTransitions(afd, 2, 2)
    addTransition(afd, 2, 'a', 2)
    addTransition(afd, 2, 'b', 2)
    setFinal(afd, 2)
    return (afd,)

def main():
    afd = myAFD()[0]
    str = [''] * (4)
    stop = 0
    str[0] = 'a'
    str[1] = 'b'
    str[2] = 'b'
    str[3] = 'a'
    stop = runAFD(afd, str, 4)[0]
    print(stop, end='')
    print(' ', end='')
    print(isFinal(afd, stop)[0], end='')
    print()
    str[0] = 'a'
    str[1] = 'b'
    str[2] = 'a'
    str[3] = 'b'
    stop = runAFD(afd, str, 4)[0]
    print(stop, end='')
    print(' ', end='')
    print(isFinal(afd, stop)[0], end='')
    print()

if __name__ == "__main__":
    main()
