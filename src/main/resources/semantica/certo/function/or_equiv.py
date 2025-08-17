def or_(a, b):
    return (not (((not (a) and not (b)))),)

def main():
    a = True
    b = True
    print(or_(a, b)[0], end='')
    print()
    a = True
    b = False
    print(or_(a, b)[0], end='')
    print()
    a = False
    b = True
    print(or_(a, b)[0], end='')
    print()
    a = False
    b = False
    print(or_(a, b)[0], end='')
    print()

if __name__ == "__main__":
    main()
