.class public teste0
.super java/lang/Object

.method public <init>()V
    aload_0
    invokenonvirtual java/lang/Object/<init>()V
    return
.end method

.method public static main([Ljava/lang/String;)V
    .limit stack 10
    .limit locals 10
    invokestatic teste0/_main()V
    return
.end method

.method public static _main()V
    .limit stack 10
    .limit locals 10
    ldc 5
    istore 0
    iload 0
    istore 1
    iconst_0
    istore 2
L0:
    iload 2
    iload 0
    if_icmpge L1
    iconst_0
    istore 3
L2:
    iload 3
    iload 1
    if_icmpge L3
    getstatic java/lang/System/out Ljava/io/PrintStream;
    ldc 42
    invokevirtual java/io/PrintStream/print(C)V
    iinc 3 1
    goto L2
L3:
    iload 1
    ldc 1
    isub
    istore 1
    getstatic java/lang/System/out Ljava/io/PrintStream;
    ldc 10
    invokevirtual java/io/PrintStream/print(C)V
    iinc 2 1
    goto L0
L1:
    return
.end method


