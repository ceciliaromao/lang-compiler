.class public teste1
.super java/lang/Object

.method public <init>()V
    aload_0
    invokenonvirtual java/lang/Object/<init>()V
    return
.end method

.method public static main([Ljava/lang/String;)V
    .limit stack 10
    .limit locals 10
    invokestatic teste1/_main()V
    return
.end method

.method public static _main()V
    .limit stack 10
    .limit locals 10
    ldc 5
    istore 0
    iload 0
    istore 2
    iconst_0
    istore 1
L0:
    iload 1
    iload 2
    if_icmpge L1
    iload 0
    istore 3
    iload 3
    istore 5
    iconst_0
    istore 4
L2:
    iload 4
    iload 5
    if_icmpge L3
    getstatic java/lang/System/out Ljava/io/PrintStream;
    ldc 42
    invokevirtual java/io/PrintStream/print(C)V
    iinc 4 1
    goto L2
L3:
    iload 0
    ldc 1
    isub
    istore 0
    getstatic java/lang/System/out Ljava/io/PrintStream;
    ldc 10
    invokevirtual java/io/PrintStream/print(C)V
    iinc 1 1
    goto L0
L1:
    return
.end method


